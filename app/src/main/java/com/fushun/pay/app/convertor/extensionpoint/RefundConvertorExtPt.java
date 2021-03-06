package com.fushun.pay.app.convertor.extensionpoint;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.convertor.ConvertorI;
import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.app.dto.clientobject.RefundCO;
import com.fushun.pay.domain.refund.entity.RefundE;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月04日00时00分
 */
public interface RefundConvertorExtPt<T extends RefundCO> extends ConvertorI, ExtensionPointI {

    public RefundE clientToEntity(T refundCO, Context context);
}
