package com.fushun.pay.app.thirdparty.extension.notify;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyWeixinGZHCO;
import com.fushun.pay.app.thirdparty.extensionpoint.PayNotifyThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatGZHPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时29分
 */
@Extension(bizCode = BizCode.CREATEPAY_WEIXIN_GZH)
public class PayNotifyWeixinGZHThirdPartyExt implements PayNotifyThirdPartyExtPt<PayNotifyWeixinGZHCO> {

    private Logger logger = LoggerFactory.getLogger(PayNotifyWeixinGZHThirdPartyExt.class);

    @Autowired
    private WeChatGZHPayFacade payFacade;

    @Override
    public PayNotifyCO created(PayNotifyWeixinGZHCO payNotifyWeixinGZHCO) {
        payFacade.payNotifyAlipayReust(payNotifyWeixinGZHCO.getParamMap(), payNotifyWeixinGZHCO);
        return payNotifyWeixinGZHCO;
    }
}
