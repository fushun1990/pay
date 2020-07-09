package com.fushun.pay.clent;

import com.fushun.framework.util.response.ApiResult;
import com.fushun.pay.dto.clientobject.createpay.CreatePayWeiXinGZHDTO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatePayWeiXinGZHVO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHValidatorDTO;
import com.fushun.pay.dto.enumeration.EPayWay;

public class Pay {

    /**
     * 公众号支付
     * @param createPayWeiXinGZHDTO
     */
    public static void payGZH(CreatePayWeiXinGZHDTO createPayWeiXinGZHDTO){
        createPayWeiXinGZHDTO.setPayWay(EPayWay.PAY_WAY_WEIXINPAY);
//        createPayWeiXinGZHDTO.setPayFrom(EPayFrom.PAY_PROPERTY);
        ApiResult<CreatePayWeiXinGZHVO> apiResult=null;
    }

    /**
     * 公众号支付，同步支付验证
     * {"responseStr":"微信返回参数","orderPayNo":"订单支付单号"}
     * @param paySyncResponseWeixinGZHValidatorDTO
     */
    public static void payGZHResponseValidatorGZH(PaySyncResponseWeixinGZHValidatorDTO paySyncResponseWeixinGZHValidatorDTO){

    }
}
