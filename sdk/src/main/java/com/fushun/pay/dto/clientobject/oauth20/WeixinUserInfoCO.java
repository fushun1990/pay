package com.fushun.pay.dto.clientobject.oauth20;

import com.fushun.pay.dto.clientobject.OAuth20CO;
import lombok.Data;

@Data
public class WeixinUserInfoCO extends OAuth20CO {

    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String accessToken;

    /**
     * 用户的唯一标识
     */
    private String openId;

}
