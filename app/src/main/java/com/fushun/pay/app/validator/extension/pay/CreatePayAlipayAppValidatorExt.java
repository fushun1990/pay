package com.fushun.pay.app.validator.extension.pay;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.validator.CreatePayValidator;
import com.fushun.pay.app.validator.extensionpoint.CreatePayValidatorExtPt;
import com.fushun.pay.client.dto.cmd.createdpay.CreatePayAlipayAppCmd;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日22时58分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_APP)
public class CreatePayAlipayAppValidatorExt implements CreatePayValidatorExtPt<CreatePayAlipayAppCmd> {


    private Logger logger = LoggerFactory.getLogger(CreatePayAlipayAppValidatorExt.class);

    @Autowired
    private CreatePayValidator payValidator;

    @Override
    public void validate(CreatePayAlipayAppCmd candidate) {
        payValidator.validate(candidate);
        logger.debug("Alipay  app validation");
    }
}
