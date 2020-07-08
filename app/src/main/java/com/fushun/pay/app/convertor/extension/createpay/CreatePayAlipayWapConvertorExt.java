package com.fushun.pay.app.convertor.extension.createpay;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.CreatePayConvertor;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayWapDTO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时37分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_WAP)
public class CreatePayAlipayWapConvertorExt implements CreatePayConvertorExtPt<CreatePayAlipayWapDTO> {

    @Autowired
    private CreatePayConvertor payConvertor;

    @Override
    public PayE clientToEntity(CreatePayAlipayWapDTO payCO, BizScenario bizScenario) {
        PayE payE = payConvertor.clientToEntity(payCO, bizScenario);
        return payE;
    }
}
