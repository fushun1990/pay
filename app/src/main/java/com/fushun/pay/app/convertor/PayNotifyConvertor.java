package com.fushun.pay.app.convertor;

import com.alibaba.cola.extension.BizScenario;
import com.fushun.pay.app.convertor.extensionpoint.PayNotifyConvertorExtPt;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.dto.clientobject.PayNotifyCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日22时45分
 */
@Component
public class PayNotifyConvertor implements PayNotifyConvertorExtPt<PayNotifyCO> {

    @Override
    public PayE clientToEntity(PayNotifyCO payNotifyCO, BizScenario bizScenario) {
        PayE payE = new PayE();
        payE.setTradeNo(payNotifyCO.getOutTradeNo());
        payE.setPayMoney(payNotifyCO.getPayMoney());
        payE.setPayNo(payNotifyCO.getPayNo());
        payE.setStatus(payNotifyCO.getStatus());
        payE.setBizScenario(bizScenario);
        return payE;
    }

    public PayDTO dataToClient(RecordPayDO dataObject) {
        return null;
    }
}
