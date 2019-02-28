package com.fushun.pay.app.dto.clientobject.oauth20;

import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日00时02分
 */
@Data
public class WeiXinOAuth20RssonseVO extends OAuth20ResponseVO {

    private String openId;
}
