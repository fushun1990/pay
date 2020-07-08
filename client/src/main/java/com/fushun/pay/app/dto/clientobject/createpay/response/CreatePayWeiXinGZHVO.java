package com.fushun.pay.app.dto.clientobject.createpay.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * 支付宝 app 支付返回对象
 */
@Data
public class CreatePayWeiXinGZHVO extends CreatedPayVO{

    /**
     * 微信 appid
     */
    private String appId;


    /**
     *  暂填写固定值Sign=WXPay
     */
    @JsonProperty("package")
    private String package_;

    /**
     * 随机字符串
     */
    private String nonceStr;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     *  签名
     */
    private String paySign;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 支付系统支付单号
     */
    private String orderPayNo;


}
