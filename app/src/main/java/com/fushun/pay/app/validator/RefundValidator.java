package com.fushun.pay.app.validator;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.alibaba.cola.validator.ValidatorI;
import com.fushun.pay.app.dto.RefundCmd;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日22时42分
 */
@Component
public class RefundValidator implements ValidatorI<RefundCmd> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void validate(RefundCmd candidate) {
        logger.debug("General validation");
        //TODO 验证框架
    }
}
