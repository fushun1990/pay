package com.fushun.pay.app.convertor.extension.refund;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.RefundConvertor;
import com.fushun.pay.app.convertor.extensionpoint.RefundConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.refund.RefundAlipayCO;
import com.fushun.pay.domain.refund.entity.RefundE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月04日00时02分
 */
@Extension(bizCode = BizCode.REFUND_ALIPAY)
public class RefundAlipayConvertorExt implements RefundConvertorExtPt<RefundAlipayCO> {

    @Autowired
    private RefundConvertor refundConvertor;

    @Override
    public RefundE clientToEntity(RefundAlipayCO refundCO, Context context) {
        RefundE refundE = refundConvertor.clientToEntity(refundCO, context);
        return refundE;
    }
}
