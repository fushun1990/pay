package com.fushun.pay.app.validator.extension.paynotify;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.validator.PayNotifyValidator;
import com.fushun.pay.app.validator.extensionpoint.PayNotifyValidatorExtPt;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyWeixinAppDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月21日23时37分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_WEIXIN_APP)
public class PayNotifyWeixinAppValidatorExt implements PayNotifyValidatorExtPt<PayNotifyThirdPartyWeixinAppDTO> {

    private Logger logger = LoggerFactory.getLogger(PayNotifyWeixinAppValidatorExt.class);

    @Autowired
    private PayNotifyValidator payNotifyValidator;

    @Override
    public void validate(PayNotifyThirdPartyWeixinAppDTO candidate) {
        payNotifyValidator.validate(candidate);
        logger.debug("Alipay app notify validation");
    }
}
