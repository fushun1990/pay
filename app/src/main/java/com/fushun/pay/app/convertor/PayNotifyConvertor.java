package com.fushun.pay.app.convertor;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.convertor.ConvertorI;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;
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
public class PayNotifyConvertor implements ConvertorI {

    public PayE clientToEntity(PayNotifyCO payNotifyCO, Context context) {
        PayE payE = new PayE();
        payE.setTradeNo(payNotifyCO.getOutTradeNo());
        payE.setPayMoney(payNotifyCO.getPayMoney());
        payE.setPayNo(payNotifyCO.getPayNo());
        payE.setStatus(payNotifyCO.getStatus());
        return payE;
    }

    public PayCO dataToClient(RecordPayDO dataObject) {
        return null;
    }
}
