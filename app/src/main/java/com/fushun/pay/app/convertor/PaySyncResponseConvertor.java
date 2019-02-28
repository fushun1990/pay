package com.fushun.pay.app.convertor;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.convertor.ConvertorI;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.app.dto.clientobject.PaySyncResponseCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时05分
 */
@Component
public class PaySyncResponseConvertor implements ConvertorI {
    public PayE clientToEntity(PaySyncResponseCO paySyncResponseCO, Context context) {
        PayE payE = new PayE();
        payE.setTradeNo(paySyncResponseCO.getOutTradeNo());
        payE.setStatus(paySyncResponseCO.getStatus());
        return payE;
    }

    public PayCO dataToClient(RecordPayDO dataObject) {
        return null;
    }
}
