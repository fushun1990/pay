package com.fushun.pay.start.restful;

import com.alibaba.cola.context.Context;
import com.fushun.pay.app.api.PayServiceI;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayAlipayAppCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayAlipayWapCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayAppCmd;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayWapCmd;
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
        return payServiceI.createPay(createPayAlipayAppCmd).getData();
    }

    @PostMapping("/pay/alipay/wap")
    public CreatedPayRequestBodyCO createdAlipayWay(@RequestBody CreatePayAlipayWapCO createPayAlipayWapCO) {
        CreatePayAlipayWapCmd createPayAlipayWapCmd = new CreatePayAlipayWapCmd();
        Context context = new Context();
        context.setBizCode(BizCode.CREATEPAY_ALIPAY_WAP);
        createPayAlipayWapCmd.setContext(context);
        createPayAlipayWapCmd.setCreatePayAlipayWapCO(createPayAlipayWapCO);
        return payServiceI.createPay(createPayAlipayWapCmd).getData();
    }
}
