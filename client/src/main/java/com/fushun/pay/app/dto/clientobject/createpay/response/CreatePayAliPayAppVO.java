package com.fushun.pay.app.dto.clientobject.createpay.response;

import lombok.Data;


/**
 * 支付宝 app 支付返回对象
 */
@Data
public class CreatePayAliPayAppVO extends CreatedPayVO{

    /**
     * 支付单号，业务系统的
     */
    private String orderPayNo;

    /**
     * 支付参数
     */
    private String payStr;
}
