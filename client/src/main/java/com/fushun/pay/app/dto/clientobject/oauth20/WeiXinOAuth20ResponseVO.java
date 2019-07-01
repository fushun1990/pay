package com.fushun.pay.app.dto.clientobject.oauth20;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日00时02分
 */
@Data
public class WeiXinOAuth20ResponseVO extends OAuth20ResponseVO {

    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String access_token;

    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    private BigDecimal expires_in;

    /**
     * 用户刷新access_token
     */
    private String refresh_token;
    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     */
    private String openId;

    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;
}
