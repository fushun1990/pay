package com.fushun.pay.start.restful;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.dto.SingleResponse;
import com.fushun.framework.web.exception.BadRequestException;
import com.fushun.pay.app.api.PayServiceI;
import com.fushun.pay.app.dto.clientobject.createpay.*;
import com.fushun.pay.app.dto.clientobject.oauth20.OAuth20ResponseVO;
import com.fushun.pay.app.dto.clientobject.oauth20.WeixinOauth20CO;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayAppCmd;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayWapCmd;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayWeixinGZHCmd;
import com.fushun.pay.app.dto.cmd.oauth20.Oauth20WeixinCmd;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年03月15日22时37分
 */
@RestController
public class PayController {

    @Autowired
    private PayServiceI payServiceI;

    /**
     * 支付宝 app
     * @param createPayAlipayAppCO
     * @return com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO
     * @description
     * @date 2019年03月15日22时43分
     * @author wangfushun
     * @version 1.0
     */
    @PostMapping("/pay/alipay/app")
    public CreatedPayRequestBodyCO createdPay(@RequestBody CreatePayAlipayAppCO createPayAlipayAppCO) {
        CreatePayAlipayAppCmd createPayAlipayAppCmd = new CreatePayAlipayAppCmd();
        Context context = new Context();
        context.setBizCode(BizCode.CREATEPAY_ALIPAY_APP);
        createPayAlipayAppCmd.setContext(context);
        createPayAlipayAppCmd.setCreatePayAlipayAppCO(createPayAlipayAppCO);
        SingleResponse<CreatedPayRequestBodyCO> singleResponse= payServiceI.createPay(createPayAlipayAppCmd);
        if(!singleResponse.isSuccess()){
            throw new BadRequestException(singleResponse.getErrMessage());
        }
        return singleResponse.getData();
    }

    /**
     * 支付宝 wap
     * @param createPayAlipayWapCO
     * @return
     */
    @PostMapping("/pay/alipay/wap")
    public CreatedPayRequestBodyCO createdAlipayWay(@RequestBody CreatePayAlipayWapCO createPayAlipayWapCO) {
        CreatePayAlipayWapCmd createPayAlipayWapCmd = new CreatePayAlipayWapCmd();
        Context context = new Context();
        context.setBizCode(BizCode.CREATEPAY_ALIPAY_WAP);
        createPayAlipayWapCmd.setContext(context);
        createPayAlipayWapCmd.setCreatePayAlipayWapCO(createPayAlipayWapCO);
        SingleResponse<CreatedPayRequestBodyCO> singleResponse= payServiceI.createPay(createPayAlipayWapCmd);
        if(!singleResponse.isSuccess()){
            throw new BadRequestException(singleResponse.getErrMessage());
        }
        return singleResponse.getData();
    }

    /**
     * 微信公众号
     * @param createPayWeiXinGZHCO
     * @return
     */
    @PostMapping("/pay/weixingzh")
    public CreatedPayRequestBodyCO createdWeixinGZH(@RequestBody CreatePayWeiXinGZHCO createPayWeiXinGZHCO){
        CreatePayWeixinGZHCmd createPayAlipayWapCmd = new CreatePayWeixinGZHCmd();
        Context context = new Context();
        context.setBizCode(BizCode.CREATEPAY_WEIXIN_GZH);
        createPayAlipayWapCmd.setContext(context);
        createPayAlipayWapCmd.setCreatePayWeiXinGZHCO(createPayWeiXinGZHCO);
        SingleResponse<CreatedPayRequestBodyCO> singleResponse= payServiceI.createPay(createPayAlipayWapCmd);
        if(!singleResponse.isSuccess()){
            throw new BadRequestException(singleResponse.getErrMessage());
        }
        return singleResponse.getData();
    }

}
