package com.fushun.pay.app.thirdparty.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.dto.clientobject.PayNotifyDTO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日19时35分
 */
public interface PayNotifyThirdPartyExtPt<T extends PayNotifyDTO> extends ExtensionPointI {

    public PayNotifyThirdPartyDTO created(T payNotifyDTO);
}
