package com.fushun.pay.domain.refund.repository;

import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.domain.refund.convertor.RefundDomainConverter;
import com.fushun.pay.domain.refund.entity.RefundE;
import com.fushun.pay.infrastructure.refund.tunnel.database.RefundDBTunnel;
import com.fushun.pay.infrastructure.refund.tunnel.database.dataobject.RefundDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月04日00时09分
 */
@Component
public class RefundRepository {

    @Autowired
    private RefundDBTunnel refundDBTunnel;

    @Autowired
    private RefundDomainConverter refundDomainConverter;

    /**
     * @param refundDO
     * @return void
     * @description 托管对保存数据
     * @date 2019年02月09日10时17分
     * @author wangfushun
     * @version 1.0
     */
    public void update(RefundDO refundDO) {
        refundDBTunnel.save(refundDO);
    }

    public RefundDO findByRefundNoAndPayWay(String refundNo, EPayWay ePayWay) {
        return refundDBTunnel.findByRefundNoAndPayWay(refundNo, ePayWay.getCode());
    }

    /**
     * @param refundE
     * @return void
     * @description 创建退款信息
     * @date 2019年02月09日10时18分
     * @author wangfushun
     * @version 1.0
     */
    public void create(RefundE refundE) {
        refundDBTunnel.save(refundDomainConverter.entityToData(refundE));
    }

}
