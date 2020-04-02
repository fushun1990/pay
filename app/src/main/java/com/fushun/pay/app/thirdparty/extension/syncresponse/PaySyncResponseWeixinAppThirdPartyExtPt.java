package com.fushun.pay.app.thirdparty.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.dto.clientobject.PaySyncResponseCO;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseWeixinAppCO;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatAppPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月23日23时58分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_WEIXIN_APP)
public class PaySyncResponseWeixinAppThirdPartyExtPt implements PaySyncResponseThirdPartyExtPt<PaySyncResponseWeixinAppCO> {

    @Autowired
    private WeChatAppPayFacade alipayWapPayFacade;

    @Override
    public PaySyncResponseCO responseValidator(PaySyncResponseWeixinAppCO paySyncResponseWeixinAppCO) {
        alipayWapPayFacade.payResultAlipayReust(paySyncResponseWeixinAppCO.getResponseStr(), paySyncResponseWeixinAppCO);
        return paySyncResponseWeixinAppCO;
    }
}
