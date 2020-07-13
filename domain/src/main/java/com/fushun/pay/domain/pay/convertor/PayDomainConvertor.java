package com.fushun.pay.domain.pay.convertor;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日18时29分
 */
@Component
public class PayDomainConvertor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RecordPayDO entityToData(PayE payE) {
        RecordPayDO recordPayDO = new RecordPayDO();
        entityToData(payE, recordPayDO);
        return recordPayDO;
    }

    public RecordPayDO entityToData(PayE payE, RecordPayDO recordPayDO) {
        String outTradeNo = payE.getPayFrom().getPreStr() + payE.getTradeNo();
        recordPayDO.setOutTradeNo(outTradeNo);
        recordPayDO.setOrderPayNo(payE.getTradeNo());
        recordPayDO.setPayMoney(payE.getPayMoney());
        recordPayDO.setPayWay(payE.getPayWay());
        recordPayDO.setPayFrom(payE.getPayFrom());
        recordPayDO.setRefundAmount(payE.getRefundAmount());
        recordPayDO.setStatus(payE.getStatus());
        recordPayDO.setNotityStatus(payE.getNotityStatus());
        recordPayDO.setNotifyUrl(payE.getNotifyUrl());
        recordPayDO.setReturnUrl(payE.getReturnUrl());
        return recordPayDO;
    }
}
