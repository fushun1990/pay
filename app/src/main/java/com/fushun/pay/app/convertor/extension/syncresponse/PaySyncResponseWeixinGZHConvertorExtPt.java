package com.fushun.pay.app.convertor.extension.syncresponse;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PaySyncResponseConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHDTO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时04分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.payScenario_WEIXIN_GZH)
public class PaySyncResponseWeixinGZHConvertorExtPt implements PaySyncResponseConvertorExtPt<PaySyncResponseWeixinGZHDTO> {

    @Autowired
    private PaySyncResponseConvertor payConvertor;

    @Override
    public PayE clientToEntity(PaySyncResponseWeixinGZHDTO paySyncResponseWeixinGZHDTO, BizScenario bizScenario) {
        PayE payE = payConvertor.clientToEntity(paySyncResponseWeixinGZHDTO, bizScenario);
        payE.setPayMoney(paySyncResponseWeixinGZHDTO.getPayMoney());
        payE.setPayNo(paySyncResponseWeixinGZHDTO.getPayNo());
        payE.setOutTradeNo(paySyncResponseWeixinGZHDTO.getOutTradeNo());
        payE.setReceiveWay(paySyncResponseWeixinGZHDTO.getReceiveWay());
        payE.setPayWay(paySyncResponseWeixinGZHDTO.getEPayWay());
        payE.setStatus(paySyncResponseWeixinGZHDTO.getStatus());
        return payE;
    }
}
