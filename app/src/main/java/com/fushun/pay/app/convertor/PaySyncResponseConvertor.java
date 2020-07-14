package com.fushun.pay.app.convertor;

import com.alibaba.cola.extension.BizScenario;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时05分
 */
@Component
public class PaySyncResponseConvertor implements PaySyncResponseConvertorExtPt<PaySyncResponseDTO> {


    @Override
    public PayE clientToEntity(PaySyncResponseDTO paySyncResponseDTO, BizScenario bizScenario) {
        PayE payE = new PayE();
        payE.setOutTradeNo(paySyncResponseDTO.getOutTradeNo());
        payE.setStatus(paySyncResponseDTO.getStatus());
        payE.setBizScenario(bizScenario);
        return payE;
    }

    public PayDTO dataToClient(RecordPayDO dataObject) {
        return null;
    }
}
