package com.fushun.pay.app.dto.cmd.oauth20;

import com.fushun.pay.app.dto.OAuth20Cmd;
import com.fushun.pay.app.dto.clientobject.OAuth20CO;
import com.fushun.pay.app.dto.clientobject.oauth20.WeixinUserInfoCO;
import lombok.Data;

/**
 * 获取用户信息
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月31日21时31分
 */
@Data
public class UserInfoWeixinCmd extends OAuth20Cmd {

    private WeixinUserInfoCO weixinUserInfoCO;

    @Override
    public WeixinUserInfoCO getOAuth20CO() {
        return this.weixinUserInfoCO;
    }
}
