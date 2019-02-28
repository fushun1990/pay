package com.fushun.pay.app.thirdparty.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日19时35分
 */
public interface CreatePayThirdPartyExtPt<T extends PayCO> extends ExtensionPointI {

    public CreatedPayRequestBodyCO created(T payCO);
}
