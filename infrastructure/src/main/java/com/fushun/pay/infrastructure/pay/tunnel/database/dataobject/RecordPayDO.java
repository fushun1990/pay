package com.fushun.pay.infrastructure.pay.tunnel.database.dataobject;

import com.fushun.framework.base.BaseCMP;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the superisong_ep_recordPay database table.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_renan_pay_record")
@IdClass(RecordPayId.class)
@Data
public class RecordPayDO extends BaseCMP implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付号（支付系统）
     */
    @Id
    @Column(columnDefinition = "varchar(100) NOT NULL comment '支付号（支付系统）'")
    private String outTradeNo;

    /**
     * 订单支付号（内部系统）
     */
    @Column(columnDefinition = "varchar(100) DEFAULT NULL comment '订单支付号（内部系统）'")
    private String orderPayNo;

    /**
     * 支付账号
     */
    @Column(columnDefinition = "varchar(50) DEFAULT NULL comment '支付账号'")
    private String payAccount;

    /**
     * 支付日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime DEFAULT NULL comment '支付日期'")
    private Date payDate;

    /**
     * 支付金额
     */
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0 comment '支付金额'")
    private BigDecimal payMoney;

    /**
     * 支付单号（第三方系统）
     */
    @Column(columnDefinition = "varchar(100) DEFAULT NULL comment '支付单号（第三方系统）'")
    private String payNo;

    /**
     * 支付方式，1、支付宝，2银联
     */
    @Column(columnDefinition = "varchar(50) DEFAULT NULL comment '支付方式，1、支付宝，2银联'")
    private String payWay;

    /**
     * 接收账户
     */
    @Column(columnDefinition = "varchar(50) DEFAULT NULL comment '接收账户'")
    private String receiveAccourt;

    /**
     * 接收方式
     */
    @Column(columnDefinition = "varchar(50) DEFAULT NULL comment '接收方式'")
    private String receiveWay;

    /**
     * 支付状态[1 成功 2失败，3：创建]
     * {@linkplain com.fushun.pay.app.dto.enumeration.ERecordPayStatus#code}
     */
    @Column(columnDefinition = "int(2) DEFAULT 3 comment '支付状态[1 成功 2失败，3：创建]'")
    private Integer status;

    /**
     * 支付端，1、web,2、app
     */
    @Column(columnDefinition = "varchar(50) DEFAULT NULL comment '支付端，1、web,2、app'")
    private String payFrom;

    /**
     * 已退金额
     */
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0 comment '已退金额'")
    private BigDecimal refundAmount;

    /**
     * 系统通知 状态，1：已通知,2：未通知
     * {@link com.fushun.pay.app.dto.enumeration.ERecordPayNotityStatus#code}
     */
    @Column(columnDefinition = "int(2) DEFAULT 2 comment '已退金额'")
    private Integer notityStatus;

}