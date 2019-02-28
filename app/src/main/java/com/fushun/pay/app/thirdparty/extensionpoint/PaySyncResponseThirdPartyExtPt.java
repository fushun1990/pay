package com.fushun.pay.app.thirdparty.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.app.dto.clientobject.PaySyncResponseCO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日19时35分
 */
public interface PaySyncResponseThirdPartyExtPt<T extends PaySyncResponseCO> extends ExtensionPointI {

    public PaySyncResponseCO responseValidator(T paySyncResponseCO);
}
