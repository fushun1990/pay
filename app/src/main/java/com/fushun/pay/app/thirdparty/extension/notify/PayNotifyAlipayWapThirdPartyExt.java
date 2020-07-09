package com.fushun.pay.app.thirdparty.extension.notify;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.thirdparty.extensionpoint.PayNotifyThirdPartyExtPt;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.dto.clientobject.notify.PayNotifyAlipayWapDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.alipay.pay.AlipayWapPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时29分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_ALIPAY_WAP)
public class PayNotifyAlipayWapThirdPartyExt implements PayNotifyThirdPartyExtPt<PayNotifyAlipayWapDTO> {

    private Logger logger = LoggerFactory.getLogger(PayNotifyAlipayWapThirdPartyExt.class);

    @Autowired
    private AlipayWapPayFacade payFacade;

    @Override
    public PayNotifyThirdPartyDTO created(PayNotifyAlipayWapDTO payNotifyAlipayWapDTO) {
        return payFacade.payNotifyAlipayReust(payNotifyAlipayWapDTO);
    }
}
