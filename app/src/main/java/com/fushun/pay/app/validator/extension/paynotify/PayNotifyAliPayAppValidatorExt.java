package com.fushun.pay.app.validator.extension.paynotify;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.validator.PayNotifyValidator;
import com.fushun.pay.app.validator.extensionpoint.PayNotifyValidatorExtPt;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyAlipayAppDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月21日23时37分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_APP)
public class PayNotifyAliPayAppValidatorExt implements PayNotifyValidatorExtPt<PayNotifyThirdPartyAlipayAppDTO> {

    private Logger logger = LoggerFactory.getLogger(PayNotifyAliPayAppValidatorExt.class);

    @Autowired
    private PayNotifyValidator payNotifyValidator;

    @Override
    public void validate(PayNotifyThirdPartyAlipayAppDTO payNotifyThirdPartyDTO) {
        payNotifyValidator.validate(payNotifyThirdPartyDTO);
        logger.debug("Alipay app notify validation");
    }
}
