package com.tencent.service.impl;

import com.tencent.common.ApiConfigure;
import com.tencent.protocol.userinfo_protocol.UserInfoReqData;
import com.tencent.service.BaseService;

/**
 * 获取用户信息
 */
public class UserInfoService extends BaseService {

    public UserInfoService() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(ApiConfigure.USER_INFO_URL);
    }

    public String request(UserInfoReqData userInfoReqData) throws Exception {
        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String url = getApiURL();
        url+="?access_token="+userInfoReqData.getAccessToken()+"&openid="+userInfoReqData.getOpenid()+"&lang=zh_CN";
        String responseString = sendGet(url);

        return responseString;
    }
}
