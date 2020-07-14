package com.fushun.pay.app.service;


import com.alibaba.cola.extension.BizScenario;
import com.fushun.pay.client.api.RefundServiceI;
import com.fushun.pay.client.config.TestConfig;
import com.fushun.pay.client.dto.cmd.refund.RefundWeixinCmd;
import com.fushun.pay.dto.clientobject.refund.RefundWeixinCO;
import com.fushun.pay.infrastructure.common.BizCode;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
public class RefundServiceTest {

    @Autowired
    RefundServiceI refundServiceI;

    public void refund(){
        RefundWeixinCmd refundCmd=new RefundWeixinCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.REFUND_SCENARIO_WEIXIN);
        refundCmd.setBizScenario(bizScenario);

        RefundWeixinCO refundWeixinCO=new RefundWeixinCO();
        refundWeixinCO.setOutRefundNo("PPT_1594651501315");
        refundCmd.setRefundWeixinCO(refundWeixinCO);

        refundServiceI.refund(refundCmd);
    }
}
