package com.fushun.pay.app.validator;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.RefundCmd;
import com.fushun.pay.app.validator.extensionpoint.RefundValidatorExtPt;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日22时42分
 */
@Component
public class RefundValidator implements RefundValidatorExtPt<RefundCmd> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void validate(RefundCmd candidate) {
        logger.debug("General validation");
        //TODO 验证框架
    }
}
