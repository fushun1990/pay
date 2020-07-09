package com.fushun.pay.app.validator.extension.refund;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.validator.RefundValidator;
import com.fushun.pay.app.validator.extensionpoint.RefundValidatorExtPt;
import com.fushun.pay.client.dto.cmd.refund.RefundAlipayCmd;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日22时51分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.refundUseCase,scenario = BizCode.REFUND_SCENARIO_ALIPAY)
public class RefundAlipayValidator implements RefundValidatorExtPt<RefundAlipayCmd> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RefundValidator refundValidator;

    @Override
    public void validate(RefundAlipayCmd candidate) {
        logger.info("validator refund alipay,param:[{}]", candidate.getRefundCO());
        refundValidator.validate(candidate);
    }
}
