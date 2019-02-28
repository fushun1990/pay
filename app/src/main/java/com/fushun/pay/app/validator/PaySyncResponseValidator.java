package com.fushun.pay.app.validator;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.alibaba.cola.validator.ValidatorI;
import com.fushun.pay.app.dto.PaySyncResponseCmd;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月29日23时48分
 */
@Component
public class PaySyncResponseValidator implements ValidatorI<PaySyncResponseCmd> {

    private Logger logger = LoggerFactory.getLogger(PaySyncResponseValidator.class);

    @Override
    public void validate(PaySyncResponseCmd candidate) {
        logger.debug("General validation");
        //TODO 验证框架
    }
}
