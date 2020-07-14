package com.fushun.pay.app.convertor.extension.syncresponse;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PaySyncResponseConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时04分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_WEIXIN_XCX)
public class PaySyncResponseWeixinXCXConvertorExtPt implements PaySyncResponseConvertorExtPt<PaySyncResponseDTO> {

    @Autowired
    private PaySyncResponseConvertor payConvertor;

    @Override
    public PayE clientToEntity(PaySyncResponseDTO paySyncResponseDTO, BizScenario bizScenario) {
        PayE payE = payConvertor.clientToEntity(paySyncResponseDTO, bizScenario);
        payE.setPayMoney(paySyncResponseDTO.getPayMoney());
        payE.setPayNo(paySyncResponseDTO.getPayNo());
        payE.setOutTradeNo(paySyncResponseDTO.getOutTradeNo());
        payE.setReceiveWay(paySyncResponseDTO.getReceiveWay());
        payE.setPayWay(paySyncResponseDTO.getEPayWay());
        payE.setStatus(paySyncResponseDTO.getStatus());
        return payE;
    }
}
