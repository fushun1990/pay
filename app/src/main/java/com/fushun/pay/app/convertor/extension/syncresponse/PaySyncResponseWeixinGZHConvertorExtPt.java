package com.fushun.pay.app.convertor.extension.syncresponse;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PaySyncResponseConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时04分
 */
@Extension(bizCode = BizCode.CREATEPAY_WEIXIN_GZH)
public class PaySyncResponseWeixinGZHConvertorExtPt implements PaySyncResponseConvertorExtPt<PaySyncResponseWeixinGZHCO> {

    @Autowired
    private PaySyncResponseConvertor payConvertor;

    @Override
    public PayE clientToEntity(PaySyncResponseWeixinGZHCO paySyncResponseWeixinGZHCO, Context context) {
        PayE payE = payConvertor.clientToEntity(paySyncResponseWeixinGZHCO, context);
        payE.setPayMoney(paySyncResponseWeixinGZHCO.getPayMoney());
        payE.setPayNo(paySyncResponseWeixinGZHCO.getPayNo());
        payE.setOutTradeNo(paySyncResponseWeixinGZHCO.getOutTradeNo());
        payE.setReceiveWay(paySyncResponseWeixinGZHCO.getReceiveWay());
        payE.setPayWay(paySyncResponseWeixinGZHCO.getEPayWay());
        payE.setStatus(paySyncResponseWeixinGZHCO.getStatus());
        return payE;
    }
}
