package com.fushun.pay.dto.clientobject.createpay.response;

import lombok.Data;


/**
 * 支付宝 app 支付返回对象
 */
@Data
public class CreatePayAliPayWapVO extends CreatedPayVO{

    /**
     * 支付参数
     */
    private String payStr;
}
