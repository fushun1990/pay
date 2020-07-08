package com.fushun.pay.dto.clientobject.createpay;

import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日17时33分
 */
@Data
public class CreatePayWeiXinGZHDTO extends CreatePayWeiXinDTO {

    /**
     * 微信 公众号支付  授权code
     * 可以不传入，但是需要传入openId
     */
    private String weiXinAuthCode;


}
