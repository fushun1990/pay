package com.fushun.pay.client.app;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayAppDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import mockit.Mocked;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {TestConfig.class})
public class CustomerConvertorTest {

    @Mocked
    private ExtensionExecutor extensionExecutor;

    private CreatePayAlipayAppDTO customerCO;

    @Before
    public void setup() {
        customerCO = new CreatePayAlipayAppDTO();
    }

    @Test
    public void test(){

    }

//    @Test
    public void testBizOneConvert() {
        //1. prepare
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_ALIPAY_APP);
        //2. execute
        PayE customerE = extensionExecutor.execute(CreatePayConvertorExtPt.class, bizScenario, extension -> extension.clientToEntity(customerCO, bizScenario));

        //3. assert
//        Assert.assertEquals(SourceType.BIZ_ONE, customerE.get);
    }

//    @Test
    public void testBizTwoConvert() {
        //1. prepare
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.refundUseCase, BizCode.REFUND_SCENARIO_ALIPAY);
        //2. execute
        PayE customerE = extensionExecutor.execute(CreatePayConvertorExtPt.class, bizScenario, extension -> extension.clientToEntity(customerCO, bizScenario));

        //3. assert
//        Assert.assertEquals(SourceType.BIZ_TWO, customerE.getSourceType());
    }
}