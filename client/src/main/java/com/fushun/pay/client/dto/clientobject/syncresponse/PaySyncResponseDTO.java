package com.fushun.pay.client.dto.clientobject.syncresponse;

import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付，同步校验 返回对象
 */
@Data
public class PaySyncResponseDTO{

    /**
     * 支付同步返回结果
     */
    private String responseStr;

    private String outTradeNo;

    private ERecordPayStatus status;

    /**
     * 支付方式
     */
    private EPayWay ePayWay;

    /**
     * 接收方式
     */
    private EPayWay receiveWay;

    /**
     * 第三方 支付单号
     */
    private String payNo;

    /**
     * 第三方支付返回金额
     */
    private BigDecimal payMoney;
}
