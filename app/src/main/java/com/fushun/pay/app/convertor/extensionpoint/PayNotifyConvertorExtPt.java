package com.fushun.pay.app.convertor.extensionpoint;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.convertor.ConvertorI;
import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;
import com.fushun.pay.domain.pay.entity.PayE;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付通知
 * @creation 2019年01月22日22时32分
 */
public interface PayNotifyConvertorExtPt<T extends PayNotifyCO> extends ConvertorI, ExtensionPointI {

    public PayE clientToEntity(T payNotifyCO, Context context);
}
