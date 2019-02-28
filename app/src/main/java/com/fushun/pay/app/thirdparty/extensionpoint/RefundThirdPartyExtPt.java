package com.fushun.pay.app.thirdparty.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.app.dto.clientobject.RefundCO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日22时59分
 */
public interface RefundThirdPartyExtPt<T extends RefundCO> extends ExtensionPointI {

    public RefundCO refund(T refundCO);
}
