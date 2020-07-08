package com.fushun.pay.dto.clientobject;

import com.fushun.pay.dto.enumeration.EPayFrom;
import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERefundFrom;
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
     * 支付号（业务系统）
     */
    private String tradeNo;

    /**
     * 退款单号（业务系统）
     */
    private String refundNo;

    /**
     * 退款单号（支付系统）
     */
    private String outRefundNo;

    /**
     * 退款单号（第三方系统）
     * 不需要填写 退款成功之后返回的
     */
    private String thirdRefundNo;

    /**
     * 支付订单的总金额
     */
    private BigDecimal payMoney;
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
    private EPayWay ePayWay;


    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 特殊支付，老系统移过来的，支付单号没有前缀
     */
    private Boolean isSpecial=false;


}
