package com.fushun.pay.app.validator.extension.pay;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.cmd.createdpay.CreatePayWeixinGZHCmd;
import com.fushun.pay.app.validator.CreatePayValidator;
import com.fushun.pay.app.validator.extensionpoint.CreatePayValidatorExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日00时10分
 */
@Extension(bizCode = BizCode.CREATEPAY_WEIXIN_GZH)
public class CreatePayWeiXinGZHValidatorExt implements CreatePayValidatorExtPt<CreatePayWeixinGZHCmd> {

    private Logger logger = LoggerFactory.getLogger(CreatePayAlipayWapValidatorExt.class);

    @Autowired
    private CreatePayValidator payValidator;

    @Override
    public void validate(CreatePayWeixinGZHCmd candidate) {
        payValidator.validate(candidate);
        logger.debug("Alipay wap validation");
    }
}
