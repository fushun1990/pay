package com.fushun.pay.thirdparty.weixin.pay;

import com.fushun.framework.exception.BusinessException;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.thirdparty.weixin.pay.listener.AResultListener;
import com.tencent.WXPay;
import com.tencent.common.GZHConfigure;
import com.tencent.protocol.oauth20_protocol.OAuth20ReqData;
import com.tencent.protocol.oauth20_protocol.OAuth20ResData;
import com.tencent.protocol.userinfo_protocol.UserInfoReqData;
import com.tencent.protocol.userinfo_protocol.UserInfoResData;
import org.springframework.stereotype.Component;

@Component
public class WeiXinOAuth20Facade {

    /**
     * 获取授权接口
     *
     * @param authCode
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     * @records <p>  fushun 2017年1月6日</p>
     */
    public OAuth20ResData getOAuth20Data(String authCode) {
        OAuth20ReqData oAuth20ReqData = new OAuth20ReqData(GZHConfigure.initMethod());
        oAuth20ReqData.setOAuthCode(authCode);
        OAuth20ResultListener resultListener = new OAuth20ResultListener();
        try {
            WXPay.oAuth20Business(oAuth20ReqData, resultListener);
            OAuth20ResData resData = resultListener.getResData();
            return resData;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new PayException(e, PayException.PayExceptionEnum.REQUEST_AUTHORIZATION_FAILED);
        }
    }


    /**
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     */
    private class OAuth20ResultListener extends AResultListener<OAuth20ResData> {

        private OAuth20ResData oAuth20ResData = null;


        @Override
        public OAuth20ResData getResData() {
            return oAuth20ResData;
        }

        @Override
        public void onSuccess(OAuth20ResData t) {
            this.oAuth20ResData = t;
        }


    }


    /**
     * 获取授权接口
     *
     * @param accessToken
     * @param openId
     * @return
     * @author fushun
     * @version V3.0
     * @creation 2017年1月6日
     * @records <p>  fushun 2017年1月6日</p>
     */
    public UserInfoResData getUserInfoByOpenId(String accessToken, String openId){
        UserInfoReqData oAuth20ReqData = new UserInfoReqData(GZHConfigure.initMethod());
        oAuth20ReqData.setAccessToken(accessToken);
        oAuth20ReqData.setOpenid(openId);
        UserInfoResultListener resultListener = new UserInfoResultListener();
        try {
            WXPay.userInfoBusiness(oAuth20ReqData, resultListener);
            UserInfoResData resData = resultListener.getResData();
            return resData;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new PayException(e, PayException.PayExceptionEnum.REQUEST_AUTHORIZATION_FAILED);
        }
    }


    /**
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     */
    private class UserInfoResultListener extends AResultListener<UserInfoResData> {

        private UserInfoResData userInfoResData = null;


        @Override
        public UserInfoResData getResData() {
            return userInfoResData;
        }

        @Override
        public void onSuccess(UserInfoResData t) {
            this.userInfoResData = t;
        }


    }
}
