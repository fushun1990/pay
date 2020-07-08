package com.fushun.pay.app.validator;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.PayNotifyCmd;
import com.fushun.pay.app.validator.extensionpoint.PayNotifyValidatorExtPt;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月21日23时40分
 */
@Component
public class PayNotifyValidator implements PayNotifyValidatorExtPt<PayNotifyCmd> {

    private Logger logger = LoggerFactory.getLogger(PayNotifyValidator.class);

    @Override
    public void validate(PayNotifyCmd candidate) {
        logger.debug("General validation");
        //TODO 验证框架
    }
}
