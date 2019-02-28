package com.fushun.pay.app.thirdparty.extension.oauth20;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.dto.clientobject.oauth20.OAuth20ResponseVO;
import com.fushun.pay.app.dto.clientobject.oauth20.WeiXinOAuth20RssonseVO;
import com.fushun.pay.app.dto.clientobject.oauth20.WeixinOauth20CO;
import com.fushun.pay.app.thirdparty.extensionpoint.OAuth20ThirartExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatGZHPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月02日23时55分
 */
@Extension(bizCode = BizCode.OAUTH20_WEIXIN)
public class WeixinOAuth20ThirdPartyExt implements OAuth20ThirartExtPt<WeixinOauth20CO> {

    @Autowired
    private WeChatGZHPayFacade weChatOfficialAccountsPayFacade;

    @Override
    public OAuth20ResponseVO getOAuth20(WeixinOauth20CO weixinOauth20CO) {
        String openId = weChatOfficialAccountsPayFacade.getWeChatOpenIdByCode(weixinOauth20CO.getCode());
        WeiXinOAuth20RssonseVO weiXinOAuth20RssonseVO = new WeiXinOAuth20RssonseVO();
        weiXinOAuth20RssonseVO.setOpenId(openId);
        return weiXinOAuth20RssonseVO;
    }
}
