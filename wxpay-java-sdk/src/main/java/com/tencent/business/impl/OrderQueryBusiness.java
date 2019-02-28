package com.tencent.business.impl;

import com.tencent.business.BaseBusiness;
import com.tencent.business.ResultListener;
import com.tencent.common.ApiConfigure;
import com.tencent.common.Log;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.common.report.ReporterFactory;
import com.tencent.protocol.order_query_protocol.OrderQueryReqData;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;
import com.tencent.protocol.report_protocol.ReportReqData;
import com.tencent.service.impl.OrderQueryService;
import com.tencent.service.impl.ReportService;
import com.thoughtworks.xstream.io.StreamException;
import org.slf4j.LoggerFactory;

public class OrderQueryBusiness extends BaseBusiness<OrderQueryReqData, OrderQueryResData> {

    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(OrderQueryBusiness.class));
    //执行结果
    private static String result = "";
    private OrderQueryService orderQueryService;


    public OrderQueryBusiness() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        orderQueryService = new OrderQueryService();
    }

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        OrderQueryBusiness.result = result;
    }

    @Override
    protected void execute(OrderQueryReqData orderQueryReqData, ResultListener<OrderQueryResData> resultListener) throws Exception {
        //--------------------------------------------------------------------
        //构造请求“订单查询API”所需要提交的数据
        //--------------------------------------------------------------------

        //API返回的数据
        String orderQueryServiceResponseString;

        long costTimeStart = System.currentTimeMillis();

        //支持加载本地测试数据进行调试

        log.i("订单查询API返回的数据如下：");
        orderQueryServiceResponseString = orderQueryService.request(orderQueryReqData);


        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");

        log.i(orderQueryServiceResponseString);

        OrderQueryResData orderQueryResData = null;


        try {
            log.i("订单查询API系统成功返回数据");
            if (!Signature.checkIsSignValidFromResponseString(orderQueryServiceResponseString, orderQueryReqData.getConfigure())) {
                setResult("Case3:订单查询请求API返回的数据签名验证失败，有可能数据被篡改了", Log.LOG_TYPE_ERROR);
                resultListener.onFailBySignInvalid(orderQueryResData);
                return;
            }
            //注意，这里失败的时候是返回xml数据，成功的时候反而返回非xml数据
            orderQueryResData = Util.getObjectFromXML(orderQueryServiceResponseString, OrderQueryResData.class);

            if (orderQueryResData == null || orderQueryResData.getReturn_code() == null) {
                setResult("Case1:订单查询API请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问", Log.LOG_TYPE_ERROR);
                resultListener.onFail(orderQueryResData);
                return;
            }
            if (orderQueryResData.getReturn_code().equals("FAIL")) {
                ///注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
                setResult("Case2:订单查询API系统返回失败，请检测Post给API的数据是否规范合法", Log.LOG_TYPE_ERROR);
                resultListener.onFail(orderQueryResData);
                return;
            }

            //--------------------------------------------------------------------
            //收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            //--------------------------------------------------------------------
            if (orderQueryResData.getResult_code().equals("FAIL")) {
                log.i("出错，错误码：" + orderQueryResData.getErr_code() + "     错误信息：" + orderQueryResData.getErr_code_des());
                setResult("Case4:【订单查询失败】", Log.LOG_TYPE_ERROR);
                //订单查询失败时再怎么延时查询订单查询状态都没有意义，这个时间建议要么再手动重试一次，依然失败的话请走投诉渠道进行投诉
                resultListener.onFail(orderQueryResData);
                return;
            }

            //订单查询成功
            setResult("Case5:【订单查询成功】", Log.LOG_TYPE_INFO);
            resultListener.onSuccess(orderQueryResData);


        } catch (StreamException e) {
            //注意，这里成功的时候是直接返回纯文本的订单查询文本数据，非XML格式
            if (orderQueryServiceResponseString.equals(null) || orderQueryServiceResponseString.equals("")) {
                setResult("Case4:订单查询API系统返回数据为空", Log.LOG_TYPE_ERROR);
                resultListener.onFail(orderQueryServiceResponseString);
            } else {
                setResult("Case3:订单查询API系统成功返回数据", Log.LOG_TYPE_INFO);
                resultListener.onFail(orderQueryServiceResponseString);
            }
        } finally {
            ReportReqData reportReqData = new ReportReqData(orderQueryReqData.getDevice_info(), orderQueryReqData.getConfigure());
            reportReqData.setInterface_url(ApiConfigure.PAY_QUERY_API);
            reportReqData.setExecute_time_cost((int) (totalTimeCost));
            reportReqData.setReturn_code(orderQueryResData.getReturn_code());
            reportReqData.setReturn_msg(orderQueryResData.getReturn_msg());
            reportReqData.setResult_code(orderQueryResData.getResult_code());
            reportReqData.setErr_code(orderQueryResData.getErr_code());
            reportReqData.setErr_code_des(orderQueryResData.getErr_code_des());
            reportReqData.setOut_trade_no(orderQueryResData.getOut_trade_no());
            reportReqData.setUser_ip(orderQueryReqData.getConfigure().getIp());

            long timeAfterReport;
            if (orderQueryReqData.getConfigure().isUseThreadToDoReport()) {
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

    public void setResult(String result, String type) {
        setResult(result);
        log.log(type, result);
    }


}
