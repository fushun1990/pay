package com.fushun.pay.app.thirdparty.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseAlipayAppValidatorDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.alipay.pay.AlipayAppPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月23日23时58分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_APP)
public class PaySyncResponseAlipayAppThirdPartyExtPt implements PaySyncResponseThirdPartyExtPt<PaySyncResponseAlipayAppValidatorDTO> {

    @Autowired
    private AlipayAppPayFacade payFacade;

    @Override
    public PaySyncResponseDTO responseValidator(PaySyncResponseAlipayAppValidatorDTO paySyncResponseValidatorDTO) {
        return payFacade.payResultAlipayReust(paySyncResponseValidatorDTO);
    }
}
