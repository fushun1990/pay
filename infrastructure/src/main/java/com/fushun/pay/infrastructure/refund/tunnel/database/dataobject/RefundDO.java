package com.fushun.pay.infrastructure.refund.tunnel.database.dataobject;

import com.fushun.framework.base.BaseCMP;
import com.fushun.pay.dto.enumeration.ERefundNotityStatus;
import com.fushun.pay.app.dto.enumeration.ERefundStatus;
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
@Table(name = "t_renan_pay_refund")
@Data
public class RefundDO extends BaseCMP implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支付单号（支付系统）
     */
    @Column(columnDefinition = "varchar(100) DEFAULT NULL comment '支付单号（支付系统）'")
    private String outTradeNo;

    /**
     * 支付方式
     */
    @Column(columnDefinition = "varchar(50) DEFAULT NULL comment '支付方式'")
    private String payWay;

    /**
     * 退款源
     */
    @Column(columnDefinition = "varchar(50) DEFAULT NULL comment '退款源'")
    private String refundFrom;

    /**
     * 退款金额
     */
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0 comment '退款金额'")
    private BigDecimal refundMoney;

    /**
     * 退款单号（内部系统）
     */
    @Column(columnDefinition = "varchar(100) DEFAULT NULL comment '退款单号（业务系统）'")
    private String refundNo;

    /**
     * 退款单号（支付系统）
     */
    @Column(columnDefinition = "varchar(100) DEFAULT NULL comment '退款单号（支付系统）'")
    private String outRefundNo;

    /**
     * 退款单号（第三方）
     */
    @Column(columnDefinition = "varchar(100) DEFAULT NULL comment '退款单号（第三方）'")
    private String thirdRefundNo;

    /**
     * 退款原因
     */
    @Column(columnDefinition = "varchar(100) DEFAULT NULL comment '退款原因'")
    private String refundReason;

    @Column(columnDefinition = "varchar(4000) DEFAULT NULL comment '失败原因'")
    private String result;

    /**
     * 退款状态
     */
    @Column(columnDefinition = "varchar(10) DEFAULT 'WAIT' comment '退款状态1:等待退款,2:退款成功,3:退款失败'")
    @Enumerated(EnumType.STRING)
    private ERefundStatus status;

    /**
     * 通知状态 1:已通知，2：未通知
     */
    @Column(columnDefinition = "varchar(10) DEFAULT 'NO' comment '通知状态 1:已通知，2：未通知'")
    @Enumerated(EnumType.STRING)
    private ERefundNotityStatus noticeStatus;

}