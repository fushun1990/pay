package com.fushun.pay.app.thirdparty.extension.notify;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.thirdparty.extensionpoint.PayNotifyThirdPartyExtPt;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.dto.clientobject.notify.PayNotifyWeixinGZHDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatGZHPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时29分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.payScenario_WEIXIN_GZH)
public class PayNotifyWeixinGZHThirdPartyExt implements PayNotifyThirdPartyExtPt<PayNotifyWeixinGZHDTO> {

    private Logger logger = LoggerFactory.getLogger(PayNotifyWeixinGZHThirdPartyExt.class);

    @Autowired
    private WeChatGZHPayFacade payFacade;

    @Override
    public PayNotifyThirdPartyDTO created(PayNotifyWeixinGZHDTO payNotifyWeixinGZHDTO) {
        return payFacade.payNotifyAlipayReust(payNotifyWeixinGZHDTO);
    }
}
