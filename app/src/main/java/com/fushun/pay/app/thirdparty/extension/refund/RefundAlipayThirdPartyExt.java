package com.fushun.pay.app.thirdparty.extension.refund;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.dto.clientobject.RefundCO;
import com.fushun.pay.dto.clientobject.refund.RefundAlipayCO;
import com.fushun.pay.app.thirdparty.extensionpoint.RefundThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.alipay.refund.AlipayAppRefundFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日23时01分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.refundUseCase,scenario = BizCode.REFUND_SCENARIO_ALIPAY)
public class RefundAlipayThirdPartyExt implements RefundThirdPartyExtPt<RefundAlipayCO> {

    @Autowired
    private AlipayAppRefundFacade alipayAppRefundFacade;

    @Override
    public RefundCO refund(RefundAlipayCO refundAlipayCO) {
        alipayAppRefundFacade.refundRequest(refundAlipayCO);
        return null;
    }
}
