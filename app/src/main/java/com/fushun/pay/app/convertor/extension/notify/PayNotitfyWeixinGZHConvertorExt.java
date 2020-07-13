package com.fushun.pay.app.convertor.extension.notify;

import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.convertor.PayNotifyConvertor;
import com.fushun.pay.app.convertor.extensionpoint.PayNotifyConvertorExtPt;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyWeixinGZHDTO;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日22时44分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.PAY_SCENARIO_WEIXIN_GZH)
public class PayNotitfyWeixinGZHConvertorExt implements PayNotifyConvertorExtPt<PayNotifyThirdPartyWeixinGZHDTO> {

    @Autowired
    private PayNotifyConvertor payConvertor;

    @Override
    public PayE clientToEntity(PayNotifyThirdPartyWeixinGZHDTO payNotifyThirdPartyWeixinGZHDTO, BizScenario bizScenario) {
        PayE payE = payConvertor.clientToEntity(payNotifyThirdPartyWeixinGZHDTO, bizScenario);
        return payE;
    }
}
