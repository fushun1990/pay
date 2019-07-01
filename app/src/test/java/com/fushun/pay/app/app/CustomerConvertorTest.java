package com.fushun.pay.app.app;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.fushun.pay.app.config.TestConfig;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayAlipayAppCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import mockit.Mocked;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {TestConfig.class})
public class CustomerConvertorTest {

    @Mocked
    private ExtensionExecutor extensionExecutor;

    private CreatePayAlipayAppCO customerCO;

    @Before
    public void setup() {
        customerCO = new CreatePayAlipayAppCO();
    }

    @Test
    public void test(){

    }

//    @Test
    public void testBizOneConvert() {
        //1. prepare
        Context context = new Context();
        context.setBizCode(BizCode.CREATEPAY_ALIPAY_APP);

        //2. execute
        PayE customerE = extensionExecutor.execute(CreatePayConvertorExtPt.class, context, extension -> extension.clientToEntity(customerCO, context));

        //3. assert
//        Assert.assertEquals(SourceType.BIZ_ONE, customerE.get);
    }

//    @Test
    public void testBizTwoConvert() {
        //1. prepare
        Context context = new Context();
        context.setBizCode(BizCode.REFUND_ALIPAY);

        //2. execute
        PayE customerE = extensionExecutor.execute(CreatePayConvertorExtPt.class, context, extension -> extension.clientToEntity(customerCO, context));

        //3. assert
//        Assert.assertEquals(SourceType.BIZ_TWO, customerE.getSourceType());
    }
}