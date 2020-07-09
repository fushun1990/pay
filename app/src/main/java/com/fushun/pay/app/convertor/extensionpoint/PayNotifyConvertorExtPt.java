package com.fushun.pay.app.convertor.extensionpoint;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.domain.pay.entity.PayE;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付通知
 * @creation 2019年01月22日22时32分
 */
public interface PayNotifyConvertorExtPt<T extends PayNotifyThirdPartyDTO> extends ExtensionPointI {

    public PayE clientToEntity(T payNotifyThirdPartyDTO, BizScenario bizScenario);
}
