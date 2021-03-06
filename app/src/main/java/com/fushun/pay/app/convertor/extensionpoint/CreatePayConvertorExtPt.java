package com.fushun.pay.app.convertor.extensionpoint;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.convertor.ConvertorI;
import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.domain.pay.entity.PayE;

/**
 * @author wangfushun
 * @version 1.0
 * @description 创建支付
 * @creation 2019年01月18日23时36分
 */
public interface CreatePayConvertorExtPt<T extends PayCO> extends ConvertorI, ExtensionPointI {

    public PayE clientToEntity(T payCO, Context context);
}
