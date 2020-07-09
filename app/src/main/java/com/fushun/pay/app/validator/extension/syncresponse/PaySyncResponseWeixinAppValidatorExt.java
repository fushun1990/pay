package com.fushun.pay.app.validator.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.validator.PaySyncResponseValidator;
import com.fushun.pay.app.validator.extensionpoint.PaySyncResponseValidatorExtPt;
import com.fushun.pay.client.dto.cmd.syncresponse.PaySyncResponseWeiXinAppCmd;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月29日23时46分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_WEIXIN_APP)
public class PaySyncResponseWeixinAppValidatorExt implements PaySyncResponseValidatorExtPt<PaySyncResponseWeiXinAppCmd> {

    private Logger logger = LoggerFactory.getLogger(PaySyncResponseWeixinAppValidatorExt.class);

    @Autowired
    private PaySyncResponseValidator paySyncResponseValidator;

    @Override
    public void validate(PaySyncResponseWeiXinAppCmd candidate) {
        paySyncResponseValidator.validate(candidate);
        logger.debug("Alipay app sync response validation");
    }
}
