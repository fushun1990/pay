package com.fushun.pay.app.convertor.extension.syncresponse;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PaySyncResponseConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseWeixinAppCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时04分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_WEIXIN_APP)
public class PaySyncResponseWeixinAppConvertorExtPt implements PaySyncResponseConvertorExtPt<PaySyncResponseWeixinAppCO> {

    @Autowired
    private PaySyncResponseConvertor payConvertor;

    @Override
    public PayE clientToEntity(PaySyncResponseWeixinAppCO paySyncResponseWeixinAppCO, BizScenario bizScenario) {
        PayE payE = payConvertor.clientToEntity(paySyncResponseWeixinAppCO, bizScenario);
        payE.setPayMoney(paySyncResponseWeixinAppCO.getPayMoney());
        payE.setPayNo(paySyncResponseWeixinAppCO.getPayNo());
        payE.setOutTradeNo(paySyncResponseWeixinAppCO.getOutTradeNo());
        payE.setReceiveWay(paySyncResponseWeixinAppCO.getReceiveWay());
        payE.setPayWay(paySyncResponseWeixinAppCO.getEPayWay());
        payE.setStatus(paySyncResponseWeixinAppCO.getStatus());
        return payE;
    }
}
