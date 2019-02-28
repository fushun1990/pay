package com.fushun.pay.app.validator;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.alibaba.cola.validator.ValidatorI;
import com.fushun.pay.app.dto.CreatePayCmd;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付通用校验
 * @creation 2019年01月18日23时01分
 */
@Component
public class CreatePayValidator implements ValidatorI<CreatePayCmd> {

    private Logger logger = LoggerFactory.getLogger(CreatePayValidator.class);

    @Override
    public void validate(CreatePayCmd candidate) {
        logger.debug("General validation");
        //TODO 验证框架
        CreatePayCmd addCmd = (CreatePayCmd) candidate;
//        PayCO payCO= addCmd.getPayCO();
    }
}
