package com.tencent.business.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fushun.framework.util.beans.ConverterUtil;
import com.fushun.framework.util.util.JsonMapper;
import com.fushun.framework.util.util.JsonUtil;
import com.tencent.business.BaseBusiness;
import com.tencent.business.ResultListener;
import com.tencent.common.Log;
import com.tencent.protocol.oauth20_protocol.OAuth20ReqData;
import com.tencent.protocol.oauth20_protocol.OAuth20ResData;
import com.tencent.protocol.userinfo_protocol.UserInfoReqData;
import com.tencent.protocol.userinfo_protocol.UserInfoResData;
import com.tencent.service.impl.OAuth20Service;
import com.tencent.service.impl.UserInfoService;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 获取用户信息
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月6日
 */
public class UserInfoBusiness extends BaseBusiness<UserInfoReqData, UserInfoResData> {


    //打log用
    private static Log log = new Log(LoggerFactory.getLogger(UserInfoBusiness.class));
    //执行结果
    private static String result = "";

    private UserInfoService userInfoService;


    public UserInfoBusiness() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        userInfoService=new UserInfoService();
    }

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        UserInfoBusiness.result = result;
    }

    /**
     * 公众号授权
     *
     * @param oAuth20ReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener      商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */
    @Override
    protected void execute(UserInfoReqData oAuth20ReqData, ResultListener<UserInfoResData> resultListener) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“公众号授权API”所需要提交的数据
        //--------------------------------------------------------------------

        //API返回的数据
        String downloadBillServiceResponseString;

        long costTimeStart = System.currentTimeMillis();

        //支持加载本地测试数据进行调试

        log.i("公众号授权API返回的数据如下：");
        downloadBillServiceResponseString = userInfoService.request(oAuth20ReqData);


        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.i("api请求总耗时：" + totalTimeCost + "ms");

        log.i(downloadBillServiceResponseString);

        UserInfoResData userInfoResData = null;

        userInfoResData = JsonUtil.jsonToClass(downloadBillServiceResponseString, UserInfoResData.class);
        if (userInfoResData != null && userInfoResData.getErrcode() != null) {
            resultListener.onFail(userInfoResData);
        }
        resultListener.onSuccess(userInfoResData);

    }

    public void setResult(String result, String type) {
        setResult(result);
        log.log(type, result);
    }

}
