package com.tencent.protocol.userinfo_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;
import lombok.Data;

@Data
public class UserInfoReqData extends BaseReqData {
    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String accessToken;

    /**
     * 用户的唯一标识
     */
    private String openid;

    public UserInfoReqData(Configure configure) {
        super(null, configure);
    }


}
