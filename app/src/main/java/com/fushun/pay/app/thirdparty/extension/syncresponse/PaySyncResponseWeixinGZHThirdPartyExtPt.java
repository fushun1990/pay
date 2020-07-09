package com.fushun.pay.app.thirdparty.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHValidatorDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatGZHPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月23日23时58分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.payScenario_WEIXIN_GZH)
public class PaySyncResponseWeixinGZHThirdPartyExtPt implements PaySyncResponseThirdPartyExtPt<PaySyncResponseWeixinGZHValidatorDTO> {

    @Autowired
    private WeChatGZHPayFacade weChatGZHPayFacade;

    @Override
    public PaySyncResponseDTO responseValidator(PaySyncResponseWeixinGZHValidatorDTO paySyncResponseValidatorDTO) {
        return weChatGZHPayFacade.payResultAlipayReust(paySyncResponseValidatorDTO);
    }
}
