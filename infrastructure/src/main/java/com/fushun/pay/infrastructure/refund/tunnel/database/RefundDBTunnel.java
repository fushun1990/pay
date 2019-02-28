package com.fushun.pay.infrastructure.refund.tunnel.database;

import com.alibaba.cola.tunnel.DataTunnelI;
import com.fushun.framework.jpa.CustomerRepository;
import com.fushun.pay.infrastructure.refund.tunnel.database.dataobject.RefundDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月08日23时31分
 */
@Repository
public interface RefundDBTunnel extends DataTunnelI, CustomerRepository<RefundDO, BigDecimal> {

    /**
     * 获取退款数据
     *
     * @param refundNo 退款批次号
     * @param payWay   支付方式
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年4月10日
     */
    @Query("select s from RefundDO s where s.refundNo = ?1 and s.payWay = ?2")
    public RefundDO findByRefundNoAndPayWay(String refundNo, String payWay);
}
