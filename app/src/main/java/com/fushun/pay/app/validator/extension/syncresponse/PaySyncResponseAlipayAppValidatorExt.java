package com.fushun.pay.app.validator.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.cmd.syncresponse.PaySyncResponseAlipayAppCmd;
import com.fushun.pay.app.validator.PaySyncResponseValidator;
import com.fushun.pay.app.validator.extensionpoint.PaySyncResponseValidatorExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月29日23时46分
 */
@Extension(bizCode = BizCode.CREATEPAY_ALIPAY_APP)
public class PaySyncResponseAlipayAppValidatorExt implements PaySyncResponseValidatorExtPt<PaySyncResponseAlipayAppCmd> {

    private Logger logger = LoggerFactory.getLogger(PaySyncResponseAlipayAppValidatorExt.class);

    @Autowired
    private PaySyncResponseValidator paySyncResponseValidator;

    @Override
    public void validate(PaySyncResponseAlipayAppCmd candidate) {
        paySyncResponseValidator.validate(candidate);
        logger.debug("Alipay app sync response validation");
    }

}
