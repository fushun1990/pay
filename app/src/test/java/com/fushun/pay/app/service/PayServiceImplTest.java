package com.fushun.pay.app.service;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.app.api.PayServiceI;
import com.fushun.pay.app.config.TestConfig;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayAlipayAppCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayAlipayWapCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayAppCmd;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayWapCmd;
import com.fushun.pay.app.dto.enumeration.EPayFrom;
import com.fushun.pay.app.dto.enumeration.EPayWay;
import com.fushun.pay.infrastructure.common.BizCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月15日22时09分
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@SpringBootConfiguration
public class PayServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayServiceI payServiceI;

    @Test
    public void createPay() throws Exception {
//        testCreatePayAlipayApp();
        testCreatePayAlipayWapPay();
//        org.springframework.validation.Validator
//        org.springframework.core.convert.converter.Converter
//        org.springframework.core.convert.converter.GenericConverter
//       * `ValidatorI`  -> `org.springframework.validation.Validator`
//* `ConvertorI`  -> `org.springframework.core.convert.converter.Converter` 或者 `org.springframework.core.convert.converter.GenericConverter`
//* `ErrorCodeI` -> `org.springframework.validation.Errors
    }


    /**
     * @param
     * @return void
     * @description 创建支付宝 app 支付
     * @date 2019年02月15日22时14分
     * @author wangfushun
     * @version 1.0
     */
    public void testCreatePayAlipayApp() {
        CreatePayAlipayAppCmd createPayAlipayAppCmd = new CreatePayAlipayAppCmd();
        Context context = new Context();
        context.setBizCode(BizCode.CREATEPAY_ALIPAY_APP);
        createPayAlipayAppCmd.setContext(context);

        CreatePayAlipayAppCO createPayAlipayWapCO = new CreatePayAlipayAppCO();
        createPayAlipayWapCO.setPayWay(EPayWay.PAY_WAY_ALIPAY);
        createPayAlipayWapCO.setPayFrom(EPayFrom.PAY_FROM_APP_DOWN_VIDEO);
        createPayAlipayWapCO.setNotifyUrl("http://f.superisong.com/Home/pay/payNotice");
//        createPayAlipayWapCO.setReturnUrl("");
        createPayAlipayWapCO.setTradeNo("2015101600000005555558");
        createPayAlipayWapCO.setTotalFee(BigDecimal.valueOf(0.1));
        createPayAlipayWapCO.setSubject("支付测试");

        createPayAlipayAppCmd.setCreatePayAlipayAppCO(createPayAlipayWapCO);
        SingleResponse<CreatedPayRequestBodyCO> singleResponse = payServiceI.createPay(createPayAlipayAppCmd);
        Assert.assertTrue(singleResponse.isSuccess());
        logger.info(singleResponse.getData().getPayStr());
        HashMap<String, Object> map = JsonUtil.jsonToHashMap(singleResponse.getData().getPayStr());
        Assert.assertNotNull(map.get("orderPayNo"));
        Assert.assertNotNull(map.get("payForm"));
    }

    /**
     * 支付宝 wap 支付
     *
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月7日
     * @records <p>  fushun 2017年1月7日</p>
     */
    public void testCreatePayAlipayWapPay() {
        CreatePayAlipayWapCmd createPayAlipayWapCmd = new CreatePayAlipayWapCmd();
        Context context = new Context();
        context.setBizCode(BizCode.CREATEPAY_ALIPAY_WAP);
        createPayAlipayWapCmd.setContext(context);

        CreatePayAlipayWapCO createPayAlipayWapCO = new CreatePayAlipayWapCO();
        createPayAlipayWapCO.setPayWay(EPayWay.PAY_WAY_ALIPAY);
        createPayAlipayWapCO.setPayFrom(EPayFrom.PAY_FROM_APP_DOWN_VIDEO);
        createPayAlipayWapCO.setNotifyUrl("http://f.superisong.com/Home/pay/payNotice");
        createPayAlipayWapCO.setReturnUrl("http://f.superisong.com/Home/pay/payNotice");
        createPayAlipayWapCO.setTradeNo("20151016000000455566");
        createPayAlipayWapCO.setTotalFee(BigDecimal.valueOf(0.1));
        createPayAlipayWapCO.setSubject("支付测试");
        createPayAlipayWapCmd.setCreatePayAlipayWapCO(createPayAlipayWapCO);
        System.out.println(JsonUtil.classToJson(createPayAlipayWapCO));
        SingleResponse<CreatedPayRequestBodyCO> singleResponse = payServiceI.createPay(createPayAlipayWapCmd);
        Assert.assertTrue(singleResponse.isSuccess());
        logger.info(singleResponse.getData().getPayStr());
    }

    @Test
    public void payNotifyAlipayReust() throws Exception {
    }

    @Test
    public void payResponseValidator() throws Exception {
    }

}