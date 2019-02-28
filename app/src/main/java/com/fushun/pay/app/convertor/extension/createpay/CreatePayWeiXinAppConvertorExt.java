package com.fushun.pay.app.convertor.extension.createpay;

import com.alibaba.cola.context.Context;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.CreatePayConvertor;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayWeiXinAppCO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时38分
 */
@Extension(bizCode = BizCode.CREATEPAY_WEIXIN_APP)
public class CreatePayWeiXinAppConvertorExt implements CreatePayConvertorExtPt<CreatePayWeiXinAppCO> {

    @Autowired
    private CreatePayConvertor payConvertor;

    @Override
    public PayE clientToEntity(CreatePayWeiXinAppCO payCO, Context context) {
        PayE payE = payConvertor.clientToEntity(payCO, context);
        return payE;
    }
}
