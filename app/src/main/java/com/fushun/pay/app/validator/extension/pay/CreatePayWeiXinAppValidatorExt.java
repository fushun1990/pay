package com.fushun.pay.app.validator.extension.pay;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayWeixinAppCmd;
import com.fushun.pay.app.validator.CreatePayValidator;
import com.fushun.pay.app.validator.extensionpoint.CreatePayValidatorExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时05分
 */
@Extension(bizCode = BizCode.CREATEPAY_WEIXIN_APP)
public class CreatePayWeiXinAppValidatorExt implements CreatePayValidatorExtPt<CreatePayWeixinAppCmd> {

    private Logger logger = LoggerFactory.getLogger(CreatePayAlipayAppValidatorExt.class);

    @Autowired
    private CreatePayValidator payValidator;

    @Override
    public void validate(CreatePayWeixinAppCmd candidate) {
        payValidator.validate(candidate);
        logger.debug("Alipay validation");
    }
}
