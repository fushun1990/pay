package com.fushun.pay.app.validator.extension.pay;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayAlipayWapCmd;
import com.fushun.pay.app.validator.CreatePayValidator;
import com.fushun.pay.app.validator.extensionpoint.CreatePayValidatorExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月29日23时13分
 */
@Extension(bizCode = BizCode.CREATEPAY_ALIPAY_WAP)
public class CreatePayAlipayWapValidatorExt implements CreatePayValidatorExtPt<CreatePayAlipayWapCmd> {


    private Logger logger = LoggerFactory.getLogger(CreatePayAlipayWapValidatorExt.class);

    @Autowired
    private CreatePayValidator payValidator;

    @Override
    public void validate(CreatePayAlipayWapCmd candidate) {
        payValidator.validate(candidate);
        logger.debug("Alipay wap validation");
    }
}
