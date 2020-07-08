package com.fushun.pay.app.convertor.extension.refund;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.RefundConvertor;
import com.fushun.pay.app.convertor.extensionpoint.RefundConvertorExtPt;
import com.fushun.pay.dto.clientobject.refund.RefundWeixinCO;
import com.fushun.pay.domain.refund.entity.RefundE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月04日00时02分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.refundUseCase,scenario = BizCode.REFUND_SCENARIO_WEIXIN)
public class RefundWeixinConvertorExt implements RefundConvertorExtPt<RefundWeixinCO> {

    @Autowired
    private RefundConvertor refundConvertor;

    @Override
    public RefundE clientToEntity(RefundWeixinCO refundCO, BizScenario bizScenario) {
        RefundE refundE = refundConvertor.clientToEntity(refundCO, bizScenario);
        return refundE;
    }
}
