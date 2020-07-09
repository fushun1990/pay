package com.fushun.pay.app.convertor.extension.syncresponse;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PaySyncResponseConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseWeixinAppDTO;
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
public class PaySyncResponseWeixinAppConvertorExtPt implements PaySyncResponseConvertorExtPt<PaySyncResponseWeixinAppDTO> {

    @Autowired
    private PaySyncResponseConvertor payConvertor;

    @Override
    public PayE clientToEntity(PaySyncResponseWeixinAppDTO paySyncResponseWeixinAppDTO, BizScenario bizScenario) {
        PayE payE = payConvertor.clientToEntity(paySyncResponseWeixinAppDTO, bizScenario);
        payE.setPayMoney(paySyncResponseWeixinAppDTO.getPayMoney());
        payE.setPayNo(paySyncResponseWeixinAppDTO.getPayNo());
        payE.setOutTradeNo(paySyncResponseWeixinAppDTO.getOutTradeNo());
        payE.setReceiveWay(paySyncResponseWeixinAppDTO.getReceiveWay());
        payE.setPayWay(paySyncResponseWeixinAppDTO.getEPayWay());
        payE.setStatus(paySyncResponseWeixinAppDTO.getStatus());
        return payE;
    }
}
