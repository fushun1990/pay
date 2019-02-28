package com.fushun.pay.infrastructure.pay.tunnel.database.dataobject;

import com.fushun.framework.util.exception.base.BaseCMP;
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
@Table(name = "pay_record")
@IdClass(RecordPayId.class)
@Data
public class RecordPayDO extends BaseCMP implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单支付号（内部系统）
     */
    private String orderPayNo;

    /**
     * 支付号（支付系统）
     */
    @Id
    private String outTradeNo;

    /**
     * 支付账号
     */
    private String payAccount;

    /**
     * 支付日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 支付单号（第三方系统）
     */
    private String payNo;

    /**
     * 支付方式，1、支付宝，2银联
     */
    private String payWay;

    /**
     * 接收账户
     */
    private String receiveAccourt;

    /**
     * 接收方式
     */
    private String receiveWay;

    /**
     * 支付状态\r\n            [1 成功 2失败，3：创建]
     */
    private Integer status;

    /**
     * 支付端，1、web,2、app
     */
    private String payFrom;

    /**
     * 已退金额
     */
    private BigDecimal refundAmount;

    /**
     * 系统通知 状态，2：未通知，1：已通知
     */
    private Integer notityStatus;

}