package com.fushun.pay.app.convertor.extension.createpay;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.CreatePayConvertor;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayAlipayWapCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时37分
 */
@Extension(bizCode = BizCode.CREATEPAY_ALIPAY_WAP)
public class CreatePayAlipayWapConvertorExt implements CreatePayConvertorExtPt<CreatePayAlipayWapCO> {

    @Autowired
    private CreatePayConvertor payConvertor;

    @Override
    public PayE clientToEntity(CreatePayAlipayWapCO payCO, Context context) {
        PayE payE = payConvertor.clientToEntity(payCO, context);
        return payE;
    }
}
