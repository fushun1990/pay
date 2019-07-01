package com.fushun.pay.app.dto.clientobject.oauth20;

import com.fushun.pay.app.dto.clientobject.OAuth20CO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月31日21时30分
 */
@Data
public class WeixinOauth20CO extends OAuth20CO {

    /**
     * 微信授权返回的code
     */
    private String code;

    private String openId;
}
