package com.fushun.pay.app.thirdparty.extension.oauth20;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.dto.clientobject.oauth20.OAuth20ResponseVO;
import com.fushun.pay.dto.clientobject.oauth20.WeiXinOAuth20ResponseVO;
import com.fushun.pay.dto.clientobject.oauth20.WeixinOauth20CO;
import com.fushun.pay.app.thirdparty.extensionpoint.OAuth20ThirartExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatGZHPayFacade;
import com.tencent.protocol.oauth20_protocol.OAuth20ResData;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月02日23时55分
 * @TODO 删除
 */
@Extension(scenario = BizCode.OAUTH20_WEIXIN)

public class WeixinOAuth20ThirdPartyExt implements OAuth20ThirartExtPt<WeixinOauth20CO> {

    @Autowired
    private WeChatGZHPayFacade weChatOfficialAccountsPayFacade;

    @Override
    public OAuth20ResponseVO getOAuth20(WeixinOauth20CO weixinOauth20CO) {
        OAuth20ResData oAuth20ResData = weChatOfficialAccountsPayFacade.getAuthorizeByCode(weixinOauth20CO.getCode());
        WeiXinOAuth20ResponseVO weiXinOAuth20ResponseVO = new WeiXinOAuth20ResponseVO();
        weiXinOAuth20ResponseVO.setOpenId(oAuth20ResData.getOpenid());
        weiXinOAuth20ResponseVO.setAccess_token(oAuth20ResData.getAccess_token());
        weiXinOAuth20ResponseVO.setExpires_in(oAuth20ResData.getExpires_in());
        weiXinOAuth20ResponseVO.setRefresh_token(oAuth20ResData.getRefresh_token());
        weiXinOAuth20ResponseVO.setScope(oAuth20ResData.getScope());
        return weiXinOAuth20ResponseVO;
    }
}
