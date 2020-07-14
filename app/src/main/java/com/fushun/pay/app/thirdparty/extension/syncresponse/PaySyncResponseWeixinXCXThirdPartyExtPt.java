package com.fushun.pay.app.thirdparty.extension.syncresponse;

import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.dto.clientobject.PaySyncResponseValidatorDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatXCXPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 微信小程序
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月23日23时58分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_WEIXIN_XCX)
public class PaySyncResponseWeixinXCXThirdPartyExtPt implements PaySyncResponseThirdPartyExtPt<PaySyncResponseValidatorDTO> {

    @Autowired
    private WeChatXCXPayFacade weChatXCXPayFacade;

    @Override
    public PaySyncResponseDTO responseValidator(PaySyncResponseValidatorDTO paySyncResponseValidatorDTO) {
        return weChatXCXPayFacade.payResultAlipayReust(paySyncResponseValidatorDTO);
    }
}
