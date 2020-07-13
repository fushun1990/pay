package com.fushun.pay.app.service;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.client.api.PayServiceI;
import com.fushun.pay.client.config.TestConfig;
import com.fushun.pay.client.dto.cmd.createdpay.CreatePayAlipayAppCmd;
import com.fushun.pay.client.dto.cmd.createdpay.CreatePayAlipayWapCmd;
import com.fushun.pay.client.dto.cmd.createdpay.CreatePayWeixinGZHCmd;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayAppDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayWapDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayWeiXinGZHDTO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatePayAliPayAppVO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatePayAliPayWapVO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatePayWeiXinGZHVO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatedPayVO;
import com.fushun.pay.dto.enumeration.EPayFrom;
import com.fushun.pay.dto.enumeration.EPayWay;
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
    public void test(){

    }

//    @Test
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
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_ALIPAY_APP);
        createPayAlipayAppCmd.setBizScenario(bizScenario);

        CreatePayAlipayAppDTO createPayAlipayWapCO = new CreatePayAlipayAppDTO();
        createPayAlipayWapCO.setPayWay(EPayWay.PAY_WAY_ALIPAY);
        createPayAlipayWapCO.setPayFrom(EPayFrom.PAY_PROPERTY);
        createPayAlipayWapCO.setNotifyUrl("http://f.superisong.com/Home/pay/payNotice");
//        createPayAlipayWapCO.setReturnUrl("");
        createPayAlipayWapCO.setTradeNo("2015101600000005555558");
        createPayAlipayWapCO.setTotalFee(BigDecimal.valueOf(0.1));
        createPayAlipayWapCO.setSubject("支付测试");

        createPayAlipayAppCmd.setCreatePayAlipayAppDTO(createPayAlipayWapCO);
        SingleResponse<CreatedPayVO> singleResponse = payServiceI.createPay(createPayAlipayAppCmd);
        CreatePayAliPayAppVO createPayAliPayAppVO= (CreatePayAliPayAppVO) singleResponse.getData();
        Assert.assertTrue(singleResponse.isSuccess());
        logger.info(createPayAliPayAppVO.getPayStr());
        HashMap<String, Object> map = JsonUtil.jsonToHashMap(createPayAliPayAppVO.getPayStr());
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
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_ALIPAY_WAP);
        createPayAlipayWapCmd.setBizScenario(bizScenario);

        CreatePayAlipayWapDTO createPayAlipayWapDTO = new CreatePayAlipayWapDTO();
        createPayAlipayWapDTO.setPayWay(EPayWay.PAY_WAY_ALIPAY);
        createPayAlipayWapDTO.setPayFrom(EPayFrom.PAY_PROPERTY);
        createPayAlipayWapDTO.setNotifyUrl("http://f.superisong.com/Home/pay/payNotice");
        createPayAlipayWapDTO.setReturnUrl("http://f.superisong.com/Home/pay/payNotice");
        createPayAlipayWapDTO.setTradeNo("20151016000000455566");
        createPayAlipayWapDTO.setTotalFee(BigDecimal.valueOf(0.1));
        createPayAlipayWapDTO.setSubject("支付测试");
        createPayAlipayWapCmd.setCreatePayAlipayWapDTO(createPayAlipayWapDTO);
        System.out.println(JsonUtil.classToJson(createPayAlipayWapDTO));
        SingleResponse<CreatedPayVO> singleResponse = payServiceI.createPay(createPayAlipayWapCmd);
        Assert.assertTrue(singleResponse.isSuccess());
        CreatePayAliPayWapVO createPayAliPayWapVO= (CreatePayAliPayWapVO) singleResponse.getData();
        logger.info(createPayAliPayWapVO.getPayStr());
    }


    /**
     * 微信公众号 支付
     *
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月7日
     * @records <p>  fushun 2017年1月7日</p>
     */
    @Test
    public void testCreatePayWeiXinGZHPay() {
        CreatePayWeixinGZHCmd createPayWeixinGZHCmd = new CreatePayWeixinGZHCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_WEIXIN_GZH);
        createPayWeixinGZHCmd.setBizScenario(bizScenario);

        CreatePayWeiXinGZHDTO createPayWeiXinGZHDTO = new CreatePayWeiXinGZHDTO();
        createPayWeiXinGZHDTO.setPayWay(EPayWay.PAY_WAY_WEIXINPAY);
        createPayWeiXinGZHDTO.setPayFrom(EPayFrom.PAY_PROPERTY);
        createPayWeiXinGZHDTO.setOpenId("oQQcO5LkHoQD0FMEXJC256YhIzQ4");
        createPayWeiXinGZHDTO.setNotifyUrl("http://f.superisong.com/Home/pay/payNotice");
//        createPayAlipayWapDTO.setReturnUrl("http://f.superisong.com/Home/pay/payNotice");
        createPayWeiXinGZHDTO.setTradeNo("20151016000000455566");
        createPayWeiXinGZHDTO.setTotalFee(BigDecimal.valueOf(0.1));
        createPayWeiXinGZHDTO.setSpbillCreateIp("192.168.0.1");
        createPayWeiXinGZHDTO.setBody("物业缴费");

//        createPayAlipayWapDTO.setSubject("支付测试");
        createPayWeixinGZHCmd.setCreatePayWeiXinGZHDTO(createPayWeiXinGZHDTO);
        System.out.println(JsonUtil.classToJson(createPayWeiXinGZHDTO));
        SingleResponse<CreatedPayVO> singleResponse = payServiceI.createPay(createPayWeixinGZHCmd);
        Assert.assertTrue(singleResponse.isSuccess());
        CreatePayWeiXinGZHVO createPayAliPayWapVO= (CreatePayWeiXinGZHVO) singleResponse.getData();
        logger.info(JsonUtil.toJson(createPayAliPayWapVO));
    }

}