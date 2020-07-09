package com.fushun.pay.app.thirdparty.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.dto.clientobject.PaySyncResponseValidatorDTO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日19时35分
 */
public interface PaySyncResponseThirdPartyExtPt<T extends PaySyncResponseValidatorDTO> extends ExtensionPointI {

    public PaySyncResponseDTO responseValidator(T paySyncResponseValidatorDTO);
}
