package com.fushun.pay.app.convertor;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.convertor.ConvertorI;
import com.alibaba.cola.domain.DomainFactory;
import com.fushun.framework.util.util.EnumUtil;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.app.dto.enumeration.EPayFrom;
import com.fushun.pay.app.dto.enumeration.EPayWay;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时39分
 */
@Component
public class CreatePayConvertor implements ConvertorI {

    public PayE clientToEntity(PayCO payCO, Context context) {
        PayE payE = DomainFactory.create(PayE.class);
        String outTradeNo = payCO.getPayFrom().getPreStr() + payCO.getTradeNo();
        payE.setTradeNo(payCO.getTradeNo());
        payE.setOutTradeNo(outTradeNo);
        payE.setPayMoney(payCO.getTotalFee());
        payE.setPayWay(payCO.getPayWay());
        payE.setPayFrom(payCO.getPayFrom());
        payE.setContext(context);
        return payE;
    }

    public PayCO dataToClient(RecordPayDO dataObject) {
        PayCO payCO = new PayCO();
        payCO.setPayFrom(EnumUtil.getEnum(EPayFrom.class, dataObject.getPayFrom()));
        payCO.setPayWay(EnumUtil.getEnum(EPayWay.class, dataObject.getPayWay()));
        payCO.setTotalFee(dataObject.getPayMoney());
        payCO.setTradeNo(dataObject.getOutTradeNo());
        payCO.setOrderPayNo(dataObject.getOrderPayNo());
        payCO.setPayNo(dataObject.getPayNo());
        return payCO;
    }
}
