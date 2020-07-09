package com.fushun.pay.app.thirdparty.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseAlipayWapValidatorDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.alipay.pay.AlipayWapPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月23日23时58分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_WAP)
public class PaySyncResponseAlipayWapThirdPartyExtPt implements PaySyncResponseThirdPartyExtPt<PaySyncResponseAlipayWapValidatorDTO> {

    @Autowired
    private AlipayWapPayFacade alipayWapPayFacade;

    @Override
    public PaySyncResponseDTO responseValidator(PaySyncResponseAlipayWapValidatorDTO paySyncResponseValidatorDTO) {
        return alipayWapPayFacade.payResultAlipayReust(paySyncResponseValidatorDTO);
    }
}
