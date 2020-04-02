package com.fushun.pay.start.restful;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.BizScenario;
import com.fushun.framework.web.exception.BadRequestException;
import com.fushun.pay.app.api.PayServiceI;
import com.fushun.pay.app.dto.clientobject.createpay.*;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayAppCmd;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayWapCmd;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayWeixinGZHCmd;
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
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_ALIPAY_APP);
        createPayAlipayAppCmd.setBizScenario(bizScenario);
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
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_ALIPAY_WAP);
        createPayAlipayWapCmd.setBizScenario(bizScenario);
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
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.payScenario_WEIXIN_GZH);
        createPayAlipayWapCmd.setBizScenario(bizScenario);
        createPayAlipayWapCmd.setCreatePayWeiXinGZHCO(createPayWeiXinGZHCO);
        SingleResponse<CreatedPayRequestBodyCO> singleResponse= payServiceI.createPay(createPayAlipayWapCmd);
        if(!singleResponse.isSuccess()){
            throw new BadRequestException(singleResponse.getErrMessage());
        }
        return singleResponse.getData();
    }

}
