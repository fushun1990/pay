package com.tencent.business.impl;

import com.tencent.business.BaseBusiness;
import com.tencent.business.ResultListener;
import com.tencent.common.ApiConfigure;
import com.tencent.common.Log;
import com.tencent.common.Util;
import com.tencent.common.report.ReporterFactory;
import com.tencent.protocol.report_protocol.ReportReqData;
import com.tencent.protocol.unifiedorder_protocol.AppUnifiedOrderReqData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
import com.tencent.service.impl.ReportService;
import com.tencent.service.impl.UnifiedorderService;
import com.thoughtworks.xstream.io.StreamException;
import org.slf4j.LoggerFactory;


/**
 * 统一下单
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月4日
 */
public class UnifiedorderBusiness<T extends AppUnifiedOrderReqData> extends BaseBusiness<T, UnifiedorderResData> {

    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(UnifiedorderBusiness.class));
    //执行结果
    private static String result = "";
    private UnifiedorderService unifiedorderService;

    public UnifiedorderBusiness() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        unifiedorderService = new UnifiedorderService();
    }

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        UnifiedorderBusiness.result = result;
    }

    /**
     * 统一下单
     *
     * @param unifiedorderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener      商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */

    protected void execute(T unifiedorderReqData, ResultListener<UnifiedorderResData> resultListener) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“统一下单API”所需要提交的数据
        //--------------------------------------------------------------------

        //API返回的数据
        String unifiedOrderServiceResponseString;

        long costTimeStart = System.currentTimeMillis();

        //支持加载本地测试数据进行调试

        log.i("统一下单API返回的数据如下：");
        unifiedOrderServiceResponseString = unifiedorderService.request(unifiedorderReqData);


        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");

        log.i(unifiedOrderServiceResponseString);

        UnifiedorderResData unifiedorderResData = null;

        String returnCode = "";
        String returnMsg = "";

        try {
            //注意，这里失败的时候是返回xml数据，成功的时候反而返回非xml数据
            unifiedorderResData = Util.getObjectFromXML(unifiedOrderServiceResponseString, UnifiedorderResData.class);

            if (unifiedorderResData == null || unifiedorderResData.getReturn_code() == null) {
                setResult("Case1:统一下单API请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问", Log.LOG_TYPE_ERROR);
                returnCode = "FAIL";
                resultListener.onFail(unifiedorderResData);
                return;
            }
            if (unifiedorderResData.getReturn_code().equals("FAIL")) {
                ///注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
                setResult("Case2:统一下单API系统返回失败，请检测Post给API的数据是否规范合法", Log.LOG_TYPE_ERROR);
                resultListener.onFail(unifiedorderResData);
                returnCode = "FAIL";
                returnMsg = unifiedorderResData.getReturn_msg();
            }
            resultListener.onSuccess(unifiedorderResData);
        } catch (StreamException e) {
            //注意，这里成功的时候是直接返回纯文本的统一下单文本数据，非XML格式
            if (unifiedOrderServiceResponseString.equals(null) || unifiedOrderServiceResponseString.equals("")) {
                setResult("Case4:统一下单API系统返回数据为空", Log.LOG_TYPE_ERROR);
                resultListener.onFail(unifiedOrderServiceResponseString);
            } else {
                setResult("Case3:统一下单API系统成功返回数据", Log.LOG_TYPE_INFO);
                resultListener.onFail(unifiedOrderServiceResponseString);
            }
            returnCode = "SUCCESS";
        } finally {

            ReportReqData reportReqData = new ReportReqData(unifiedorderReqData.getDevice_info(), unifiedorderReqData.getConfigure());

            reportReqData.setInterface_url(ApiConfigure.UNIFIEDORDER_API);
            reportReqData.setExecute_time_cost((int) (totalTimeCost));
            reportReqData.setReturn_code(returnCode);
            reportReqData.setReturn_msg(returnMsg);
            reportReqData.setUser_ip(unifiedorderReqData.getConfigure().getIp());

            long timeAfterReport;
            if (unifiedorderReqData.getConfigure().isUseThreadToDoReport()) {
                ReporterFactory.getReporter(reportReqData).run();
                timeAfterReport = System.currentTimeMillis();
                Util.log("pay+report总耗时（异步方式上报）：" + (timeAfterReport - costTimeStart) + "ms");
            } else {
                ReportService.request(reportReqData);
                timeAfterReport = System.currentTimeMillis();
                Util.log("pay+report总耗时（同步方式上报）：" + (timeAfterReport - costTimeStart) + "ms");
            }
        }

    }

    public UnifiedorderService getUnifiedorderService() {
        return unifiedorderService;
    }

    public void setUnifiedorderService(UnifiedorderService unifiedorderService) {
        this.unifiedorderService = unifiedorderService;
    }

    public void setResult(String result, String type) {
        setResult(result);
        log.log(type, result);
    }


}
