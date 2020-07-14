package com.fushun.pay.app.service;


import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.client.config.TestConfig;
import com.fushun.pay.dto.clientobject.PaySyncResponseValidatorDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayWeiXinGZHDTO;
import com.fushun.pay.dto.enumeration.EPayFrom;
import com.fushun.pay.dto.enumeration.EPayWay;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Calendar;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =     SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TestConfig.class})
public class PayTest {


    @Autowired
    private TestRestTemplate restTemplate;

    private HttpEntity httpEntity;


    /**
     *  微信公众号 支付
     * @throws Exception
     */
    @Test
    public void testCreatePayWeiXinGZHPay() throws Exception {
        CreatePayWeiXinGZHDTO createPayWeiXinGZHDTO = new CreatePayWeiXinGZHDTO();
        createPayWeiXinGZHDTO.setPayWay(EPayWay.PAY_WAY_WEIXINPAY);
        createPayWeiXinGZHDTO.setPayFrom(EPayFrom.PAY_PROPERTY);
        createPayWeiXinGZHDTO.setOpenId("oQQcO5LkHoQD0FMEXJC256YhIzQ4");
        createPayWeiXinGZHDTO.setNotifyUrl("http://f.superisong.com/Home/pay/payNotice");
        createPayWeiXinGZHDTO.setOrderPayNo(""+ Calendar.getInstance().getTimeInMillis());
        createPayWeiXinGZHDTO.setTotalFee(BigDecimal.valueOf(0.01));
        createPayWeiXinGZHDTO.setSpbillCreateIp("192.168.0.1");
        createPayWeiXinGZHDTO.setBody("物业缴费");

        ResponseEntity responseEntity = restTemplate.postForEntity("/pay/weixin/gzh", createPayWeiXinGZHDTO, String.class);
        //添加cookie以保持状态
//        HttpHeaders headers = new HttpHeaders();
//        String headerValue = responseEntity.getHeaders().get("Set-Cookie").toString().replace("[", "");
//        headerValue = headerValue.replace("]", "");
//        headers.set("Cookie", headerValue);
//        httpEntity = new HttpEntity(headers);
    }

    /**
     * 微信小程序支付 支付成功，同步校验
     */
    @Test
    public void testPaySyncResponseWeiXinXCX() {

        PaySyncResponseValidatorDTO paySyncResponseValidatorDTO=new PaySyncResponseValidatorDTO();
        paySyncResponseValidatorDTO.setOutTradeNo("PPT_1594646455354");
        paySyncResponseValidatorDTO.setResponseStr("{errMsg: \"requestPayment:ok\"}");
        ResponseEntity responseEntity = restTemplate.postForEntity("/pay/valid/sync/weixin/xcx", paySyncResponseValidatorDTO, String.class);
        System.out.println(JsonUtil.toJson(responseEntity.getBody()));
    }

    /**
     * 微信小程序 支付异步通知，
     */
    @Test
    public void payNotifyAlipayReust() throws InterruptedException {
        String res="<xml><appid><![CDATA[wx3afc97fe948e75e7]]></appid>\\n<bank_type><![CDATA[OTHERS]]></bank_type>\\n<cash_fee><![CDATA[1]]></cash_fee>\\n<device_info><![CDATA[WEB]]></device_info>\\n<fee_type><![CDATA[CNY]]></fee_type>\\n<is_subscribe><![CDATA[N]]></is_subscribe>\\n<mch_id><![CDATA[1516499551]]></mch_id>\\n<nonce_str><![CDATA[60l5p6zed6eqlvg556v92tubd75dslo9]]></nonce_str>\\n<openid><![CDATA[oQQcO5LkHoQD0FMEXJC256YhIzQ4]]></openid>\\n<out_trade_no><![CDATA[PPT_1594651501315]]></out_trade_no>\\n<result_code><![CDATA[SUCCESS]]></result_code>\\n<return_code><![CDATA[SUCCESS]]></return_code>\\n<sign><![CDATA[4B3BD7EB2AB70B9E2935F298B1A67C09]]></sign>\\n<time_end><![CDATA[20200713224545]]></time_end>\\n<total_fee>1</total_fee>\\n<trade_type><![CDATA[JSAPI]]></trade_type>\\n<transaction_id><![CDATA[4200000619202007136122941010]]></transaction_id>\\n</xml>";


        ResponseEntity responseEntity = restTemplate.postForEntity("/notify/weixin/gzh", res, String.class);
        System.out.println(JsonUtil.toJson(responseEntity.getBody()));
    }
}
