package com.fushun.pay.app.convertor.extension.syncresponse;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PaySyncResponseConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseAlipayAppCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时04分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_WAP)
public class PaySyncResponseAlipyWapConvertorExtPt implements PaySyncResponseConvertorExtPt<PaySyncResponseAlipayAppCO> {

    @Autowired
    private PaySyncResponseConvertor payConvertor;

    @Override
    public PayE clientToEntity(PaySyncResponseAlipayAppCO paySyncResponseAlipayAppCO, BizScenario bizScenario) {
        PayE payE = payConvertor.clientToEntity(paySyncResponseAlipayAppCO, bizScenario);
        payE.setPayMoney(paySyncResponseAlipayAppCO.getPayMoney());
        payE.setPayNo(paySyncResponseAlipayAppCO.getPayNo());
        payE.setOutTradeNo(paySyncResponseAlipayAppCO.getOutTradeNo());
        payE.setReceiveWay(paySyncResponseAlipayAppCO.getReceiveWay());
        payE.setPayWay(paySyncResponseAlipayAppCO.getEPayWay());
        payE.setStatus(paySyncResponseAlipayAppCO.getStatus());
        return payE;
    }
}
