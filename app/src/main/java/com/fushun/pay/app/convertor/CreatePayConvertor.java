package com.fushun.pay.app.convertor;

import com.alibaba.cola.domain.DomainFactory;
import com.alibaba.cola.extension.BizScenario;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时39分
 */
@Component
public class CreatePayConvertor implements CreatePayConvertorExtPt<PayDTO> {

    @Override
    public PayE clientToEntity(PayDTO payDTO, BizScenario bizScenario) {
        PayE payE = DomainFactory.create(PayE.class);
        String outTradeNo = payDTO.getPayFrom().getPreStr() + payDTO.getTradeNo();
        payE.setTradeNo(payDTO.getTradeNo());
        payE.setOutTradeNo(outTradeNo);
        payE.setPayMoney(payDTO.getTotalFee());
        payE.setPayWay(payDTO.getPayWay());
        payE.setPayFrom(payDTO.getPayFrom());
        payE.setBizScenario(bizScenario);
        payE.setNotifyUrl(payDTO.getNotifyUrl());
        return payE;
    }

    public PayDTO dataToClient(RecordPayDO dataObject) {
        PayDTO payDTO = new PayDTO();
        payDTO.setStatus(dataObject.getStatus());
        payDTO.setPayFrom( dataObject.getPayFrom());
        payDTO.setPayWay( dataObject.getPayWay());
        payDTO.setTotalFee(dataObject.getPayMoney());
        payDTO.setTradeNo(dataObject.getOutTradeNo());
        payDTO.setOrderPayNo(dataObject.getOrderPayNo());
        payDTO.setPayNo(dataObject.getPayNo());
        return payDTO;
    }
}
