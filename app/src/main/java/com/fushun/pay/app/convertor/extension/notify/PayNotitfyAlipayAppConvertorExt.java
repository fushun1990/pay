package com.fushun.pay.app.convertor.extension.notify;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PayNotifyConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PayNotifyConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyAlipayAppCO;
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
@Extension(bizCode = BizCode.CREATEPAY_ALIPAY_APP)
public class PayNotitfyAlipayAppConvertorExt implements PayNotifyConvertorExtPt<PayNotifyAlipayAppCO> {

    @Autowired
    private PayNotifyConvertor payConvertor;

    @Override
    public PayE clientToEntity(PayNotifyAlipayAppCO payAlipayAppNotifyCO, Context context) {
        payAlipayAppNotifyCO.setReceiveWay(EPayWay.PAY_WAY_ALIPAY);

        PayE payE = payConvertor.clientToEntity(payAlipayAppNotifyCO, context);
        payE.setReceiveAccourt(payAlipayAppNotifyCO.getReceiveAccourt());
        payE.setReceiveWay(payAlipayAppNotifyCO.getReceiveWay());
        return payE;
    }
}
