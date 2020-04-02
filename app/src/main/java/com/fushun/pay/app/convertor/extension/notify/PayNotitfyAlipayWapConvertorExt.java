package com.fushun.pay.app.convertor.extension.notify;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PayNotifyConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PayNotifyConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyAlipayWapCO;
import com.fushun.pay.app.dto.enumeration.EPayWay;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日22时44分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_WAP)
public class PayNotitfyAlipayWapConvertorExt implements PayNotifyConvertorExtPt<PayNotifyAlipayWapCO> {

    @Autowired
    private PayNotifyConvertor payConvertor;

    @Override
    public PayE clientToEntity(PayNotifyAlipayWapCO payNotifyAlipayWapCO, BizScenario bizScenario) {
        payNotifyAlipayWapCO.setReceiveWay(EPayWay.PAY_WAY_ALIPAY);

        PayE payE = payConvertor.clientToEntity(payNotifyAlipayWapCO, bizScenario);
        payE.setReceiveAccourt(payNotifyAlipayWapCO.getReceiveAccourt());
        payE.setReceiveWay(payNotifyAlipayWapCO.getReceiveWay());
        return payE;
    }
}
