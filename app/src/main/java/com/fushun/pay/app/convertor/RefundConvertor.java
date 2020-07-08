package com.fushun.pay.app.convertor;

import com.alibaba.cola.extension.BizScenario;
import com.fushun.pay.app.convertor.extensionpoint.RefundConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.app.dto.clientobject.RefundCO;
import com.fushun.pay.domain.refund.entity.RefundE;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日23时51分
 */
@Component
public class RefundConvertor implements RefundConvertorExtPt<RefundCO> {

    @Override
    public RefundE clientToEntity(RefundCO refundCO, BizScenario bizScenario) {
        RefundE refundE = new RefundE();
        if(!refundCO.getIsSpecial()){
            refundE.setOutTradeNo(refundCO.getERefundFrom().getEPayFrom().getPreStr()+refundCO.getTradeNo());
        }else{
            refundE.setOutTradeNo(refundCO.getTradeNo());
        }
        refundE.setEPayWay(refundCO.getEPayWay());
        refundE.setTradeNo(refundCO.getTradeNo());
        refundE.setRefundMoney(refundCO.getRefundMoney());
        refundE.setRefundNo(refundCO.getRefundNo());
        refundE.setERefundFrom(refundCO.getERefundFrom());
        refundE.setBizScenario(bizScenario);
        return refundE;
    }

    public PayCO dataToClient(RecordPayDO dataObject) {
        return null;
    }
}
