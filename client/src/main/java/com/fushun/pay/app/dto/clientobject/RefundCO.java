package com.fushun.pay.app.dto.clientobject;

import com.fushun.pay.app.dto.enumeration.ERefundFrom;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日22时43分
 */
@Data
public class RefundCO {

    /**
     * 支付号（内部系统）
     */
    private String tradeNo;

    /**
     * 退款单号（内部系统）
     */
    private String refundNo;

    /**
     * 退款单号（支付系统）
     */
    private String outRefundNo;

    /**
     * 退款单号（第三方系统）
     */
    private String thirdRefundNo;

    /**
     * 退款金额
     */
    private BigDecimal refundMoney;

    /**
     * 退款源
     */
    private ERefundFrom eRefundFrom;

    /**
     * 支付方式（）
     */
    private String ePayWay;


    /**
     * 退款原因
     */
    private String refundReason;


}
