package com.fushun.pay.app.thirdparty.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.dto.clientobject.PaySyncResponseCO;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseAlipayWapCO;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.alipay.pay.AlipayWapPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月23日23时58分
 */
@Extension(bizCode = BizCode.CREATEPAY_ALIPAY_WAP)
public class PaySyncResponseAlipayWapThirdPartyExtPt implements PaySyncResponseThirdPartyExtPt<PaySyncResponseAlipayWapCO> {

    @Autowired
    private AlipayWapPayFacade alipayWapPayFacade;

    @Override
    public PaySyncResponseCO responseValidator(PaySyncResponseAlipayWapCO paySyncResponseAlipayWapCO) {
        alipayWapPayFacade.payResultAlipayReust(paySyncResponseAlipayWapCO.getResponseStr(), paySyncResponseAlipayWapCO);
        return paySyncResponseAlipayWapCO;
    }
}
