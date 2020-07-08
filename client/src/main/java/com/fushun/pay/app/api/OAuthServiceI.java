package com.fushun.pay.app.api;

import com.alibaba.cola.dto.SingleResponse;
import com.fushun.pay.app.dto.OAuth20Cmd;
import com.fushun.pay.dto.clientobject.oauth20.OAuth20ResponseVO;

public interface OAuthServiceI {

    public SingleResponse<OAuth20ResponseVO> oahth20(OAuth20Cmd oAuth20Cmd);
}
