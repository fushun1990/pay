package com.fushun.pay.client.dto.cmd.oauth20;

import com.fushun.pay.client.dto.OAuth20Cmd;
import com.fushun.pay.dto.clientobject.oauth20.WeixinOauth20CO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月31日21时31分
 */
@Data
public class Oauth20WeixinCmd extends OAuth20Cmd {

    private WeixinOauth20CO weixinOauth20CO;

    @Override
    public WeixinOauth20CO getOAuth20CO() {
        return this.weixinOauth20CO;
    }
}
