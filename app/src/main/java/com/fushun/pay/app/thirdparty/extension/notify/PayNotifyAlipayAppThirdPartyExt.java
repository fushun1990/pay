package com.fushun.pay.app.thirdparty.extension.notify;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyAlipayAppCO;
import com.fushun.pay.app.thirdparty.extensionpoint.PayNotifyThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.alipay.pay.AlipayAppPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时29分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_APP)
public class PayNotifyAlipayAppThirdPartyExt implements PayNotifyThirdPartyExtPt<PayNotifyAlipayAppCO> {

    private Logger logger = LoggerFactory.getLogger(PayNotifyAlipayAppThirdPartyExt.class);

    @Autowired
    private AlipayAppPayFacade payFacade;

    @Override
    public PayNotifyCO created(PayNotifyAlipayAppCO payAlipayAppNotifyCO) {
        payFacade.payNotifyAlipayReust(payAlipayAppNotifyCO.getParamMap(), payAlipayAppNotifyCO);
        return payAlipayAppNotifyCO;
    }
}
