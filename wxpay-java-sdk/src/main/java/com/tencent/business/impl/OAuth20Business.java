package com.tencent.business.impl;

import cn.kidtop.framework.util.JsonUtil;
import com.tencent.business.BaseBusiness;
import com.tencent.business.ResultListener;
import com.tencent.common.Log;
import com.tencent.protocol.oauth20_protocol.OAuth20ReqData;
import com.tencent.protocol.oauth20_protocol.OAuth20ResData;
import com.tencent.service.impl.OAuth20Service;
import org.slf4j.LoggerFactory;

/**
 * 获取授权 信息
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月6日
 */
public class OAuth20Business extends BaseBusiness<OAuth20ReqData, OAuth20ResData> {


    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(OAuth20Business.class));
    //执行结果
    private static String result = "";
    private OAuth20Service oAuth20Service;

    public OAuth20Business() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        oAuth20Service = new OAuth20Service();
    }

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        OAuth20Business.result = result;
    }

    /**
     * 公众号授权
     *
     * @param unifiedorderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener      商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */
    @Override
    protected void execute(OAuth20ReqData oAuth20ReqData, ResultListener<OAuth20ResData> resultListener) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“公众号授权API”所需要提交的数据
        //--------------------------------------------------------------------

        //API返回的数据
        String downloadBillServiceResponseString;

        long costTimeStart = System.currentTimeMillis();

        //支持加载本地测试数据进行调试

        log.i("公众号授权API返回的数据如下：");
        downloadBillServiceResponseString = oAuth20Service.request(oAuth20ReqData);


        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");

        log.i(downloadBillServiceResponseString);

        OAuth20ResData oAuth20ResData = null;

        oAuth20ResData = JsonUtil.jsonToClass(downloadBillServiceResponseString, OAuth20ResData.class);
        if (oAuth20ResData != null && oAuth20ResData.getErrcode() != null) {
            resultListener.onFail(oAuth20ResData);
        }
        resultListener.onSuccess(oAuth20ResData);

    }

    public void setResult(String result, String type) {
        setResult(result);
        log.log(type, result);
    }


}
