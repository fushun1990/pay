package com.tencent.protocol.oauth20_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

public class OAuth20ReqData extends BaseReqData {

    /**
     * 授权code
     */
    private String OAuthCode;

    public OAuth20ReqData(Configure configure) {
        super(null, configure);
    }

    public String getOAuthCode() {
        return OAuthCode;
    }

    public void setOAuthCode(String oAuthCode) {
        OAuthCode = oAuthCode;
    }


}
