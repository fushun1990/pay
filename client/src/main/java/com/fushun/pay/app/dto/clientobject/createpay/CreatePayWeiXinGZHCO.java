package com.fushun.pay.app.dto.clientobject.createpay;

import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日17时33分
 */
@Data
public class CreatePayWeiXinGZHCO extends CreatePayWeiXinCO {

    /**
     * 微信 公众号支付  授权code
     */
    private String weiXinAuthCode;


}
