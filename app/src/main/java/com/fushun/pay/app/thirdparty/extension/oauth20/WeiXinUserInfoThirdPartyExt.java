package com.fushun.pay.app.thirdparty.extension.oauth20;

import com.alibaba.cola.extension.Extension;
import com.fushun.framework.util.beans.ConverterUtil;
import com.fushun.pay.app.dto.clientobject.oauth20.OAuth20ResponseVO;
import com.fushun.pay.app.dto.clientobject.oauth20.WeixinUserInfoCO;
import com.fushun.pay.app.dto.clientobject.oauth20.WeixinUserInfoResponseVO;
import com.fushun.pay.app.thirdparty.extensionpoint.OAuth20ThirartExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatGZHPayFacade;
import com.tencent.protocol.userinfo_protocol.UserInfoResData;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 获取微信用户信息
 */
@Extension(bizCode = BizCode.USER_INFO_WEIXIN)
public class WeiXinUserInfoThirdPartyExt implements OAuth20ThirartExtPt<WeixinUserInfoCO> {

    @Autowired
    private WeChatGZHPayFacade weChatOfficialAccountsPayFacade;

    @Override
    public WeixinUserInfoResponseVO getOAuth20(WeixinUserInfoCO weixinUserInfoCO) {
        UserInfoResData userInfoResData = weChatOfficialAccountsPayFacade.getUserInfoByOpenId(weixinUserInfoCO.getAccessToken(),weixinUserInfoCO.getOpenId());
        WeixinUserInfoResponseVO weiXinOAuth20RssonseVO=new WeixinUserInfoResponseVO();
        weiXinOAuth20RssonseVO.setCity(userInfoResData.getCity());
        weiXinOAuth20RssonseVO.setCountry(userInfoResData.getCountry());
        weiXinOAuth20RssonseVO.setHeadimgurl(userInfoResData.getHeadimgurl());
        weiXinOAuth20RssonseVO.setNickname(userInfoResData.getNickname());
        weiXinOAuth20RssonseVO.setOpenid(userInfoResData.getOpenid());
        weiXinOAuth20RssonseVO.setPrivilege(userInfoResData.getPrivilege());
        weiXinOAuth20RssonseVO.setProvince(userInfoResData.getProvince());
        weiXinOAuth20RssonseVO.setSex(userInfoResData.getSex());
        weiXinOAuth20RssonseVO.setUnionid(userInfoResData.getUnionid());
        return weiXinOAuth20RssonseVO;
    }
}