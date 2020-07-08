package com.fushun.pay.app.thirdparty.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatedPayVO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日19时35分
 */
public interface CreatePayThirdPartyExtPt<T extends PayDTO> extends ExtensionPointI {

    public CreatedPayVO created(T payCO);
}
