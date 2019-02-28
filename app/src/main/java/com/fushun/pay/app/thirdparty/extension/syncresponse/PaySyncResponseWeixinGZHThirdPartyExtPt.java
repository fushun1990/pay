package com.fushun.pay.app.thirdparty.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.dto.clientobject.PaySyncResponseCO;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHCO;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatGZHPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月23日23时58分
 */
@Extension(bizCode = BizCode.CREATEPAY_WEIXIN_GZH)
public class PaySyncResponseWeixinGZHThirdPartyExtPt implements PaySyncResponseThirdPartyExtPt<PaySyncResponseWeixinGZHCO> {

    @Autowired
    private WeChatGZHPayFacade alipayWapPayFacade;

    @Override
    public PaySyncResponseCO responseValidator(PaySyncResponseWeixinGZHCO paySyncResponseWeixinGZHCO) {
        alipayWapPayFacade.payResultAlipayReust(paySyncResponseWeixinGZHCO.getResponseStr(), paySyncResponseWeixinGZHCO);
        return paySyncResponseWeixinGZHCO;
    }
}
