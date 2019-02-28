package com.fushun.pay.app.thirdparty.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日19时35分
 */
public interface PayNotifyThirdPartyExtPt<T extends PayNotifyCO> extends ExtensionPointI {

    public PayNotifyCO created(T payNotifyCO);
}
