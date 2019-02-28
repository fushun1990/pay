package com.tencent.service.impl;

import com.tencent.common.ApiConfigure;
import com.tencent.protocol.oauth20_protocol.OAuth20ReqData;
import com.tencent.service.BaseService;

public class OAuth20Service extends BaseService {

    public OAuth20Service() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(ApiConfigure.ACCESS_TOKEN_URL);
    }

    public String request(OAuth20ReqData oAuth20ReqData) throws Exception {
        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String url = getApiURL();
        url += "?appid=" + oAuth20ReqData.getAppid() + "&secret=" + oAuth20ReqData.getConfigure().getAppSecret() + "&code=" + oAuth20ReqData.getOAuthCode() + "&grant_type=authorization_code";
        String responseString = sendGet(url);

        return responseString;
    }
}
