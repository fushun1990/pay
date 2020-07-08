package com.fushun.pay.app.dto.clientobject.createpay.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * 支付宝 app 支付返回对象
 */
@Data
public class CreatePayWeiXinAppVO extends CreatedPayVO{

    /**
     * 微信 appid
     */
    private String appId;

    /**
     * 微信支付分配的商户号
     */
    private String partnerid;

    /**
     * 统一下单支付id
     */
    private String prepayid;

    /**
     *  暂填写固定值Sign=WXPay
     */
    @JsonProperty("package")
    private String package_;

    /**
     * 随机字符串
     */
    private String noncestr;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     *  签名
     */
    private String sign;

    /**
     * 支付系统支付单号
     */
    private String orderPayNo;


}
