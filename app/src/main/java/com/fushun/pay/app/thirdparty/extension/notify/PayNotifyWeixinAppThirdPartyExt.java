package com.fushun.pay.app.thirdparty.extension.notify;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.thirdparty.extensionpoint.PayNotifyThirdPartyExtPt;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.dto.clientobject.notify.PayNotifyWeixinAppDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatAppPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时29分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_WEIXIN_APP)
public class PayNotifyWeixinAppThirdPartyExt implements PayNotifyThirdPartyExtPt<PayNotifyWeixinAppDTO> {

    private Logger logger = LoggerFactory.getLogger(PayNotifyWeixinAppThirdPartyExt.class);

    @Autowired
    private WeChatAppPayFacade payFacade;

    @Override
    public PayNotifyThirdPartyDTO created(PayNotifyWeixinAppDTO payNotifyWeixinAppDTO) {
        return payFacade.payNotifyAlipayReust(payNotifyWeixinAppDTO);
    }
}
