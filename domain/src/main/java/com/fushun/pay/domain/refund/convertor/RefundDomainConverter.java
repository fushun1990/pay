package com.fushun.pay.domain.refund.convertor;

import com.alibaba.cola.convertor.ConvertorI;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.clientobject.RefundCO;
import com.fushun.pay.app.dto.enumeration.ERefundNotityStatus;
import com.fushun.pay.app.dto.enumeration.ERefundStatus;
import com.fushun.pay.domain.refund.entity.RefundE;
import com.fushun.pay.infrastructure.refund.tunnel.database.dataobject.RefundDO;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月09日00时00分
 */
@Component
public class RefundDomainConverter implements ConvertorI<RefundCO, RefundE, RefundDO> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RefundDO entityToData(RefundE refundE) {
        RefundDO refundDO = new RefundDO();
        refundDO.setOutTradeNo(refundE.getERefundFrom() + refundE.getERefundFrom().getCode());
        refundDO.setPayWay(refundE.getERefundFrom().getCode());
        refundDO.setRefundFrom(refundE.getERefundFrom().getCode());
        refundDO.setRefundMoney(refundE.getRefundMoney());
        refundDO.setRefundNo(refundE.getRefundNo());
        refundDO.setOutTradeNo(refundE.getERefundFrom().getPreStr() + refundDO.getRefundNo());
        refundDO.setRefundReason(refundE.getRefundReason());
        refundDO.setStatus(ERefundStatus.wait.getCode());
        refundDO.setNoticeStatus(ERefundNotityStatus.No.getCode());
        return refundDO;
    }
}
