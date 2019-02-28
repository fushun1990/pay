package com.fushun.pay.infrastructure.refund.tunnel.database.dataobject;

import com.fushun.framework.util.exception.base.BaseCMP;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 支付订单退款记录表
 * The persistent class for the superisong_ep_recordRefund database table.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "refund")
@Data
public class RefundDO extends BaseCMP implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigDecimal id;

    /**
     * 支付单号（第三方）
     */
    private String payNo;

    /**
     * 支付单号（内部系统）
     */
    private String outTradeNo;

    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 退款源
     */
    private String refundFrom;

    /**
     * 退款金额
     */
    private BigDecimal refundMoney;

    /**
     * 退款单号（内部系统）
     */
    private String refundNo;

    /**
     * 退款单号（支付系统）
     */
    private String outRefundNo;

    /**
     * 退款单号（第三方）
     */
    private String thirdRefundNo;

    /**
     * 退款原因
     */
    private String refundReason;

    private String result;

    /**
     * 退款状态
     */
    private Integer status;

    /**
     * 通知状态 1:已通知，2：未通知
     */
    private Integer noticeStatus;

}