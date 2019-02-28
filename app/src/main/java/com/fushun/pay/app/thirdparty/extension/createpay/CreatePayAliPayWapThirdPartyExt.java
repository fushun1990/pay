package com.fushun.pay.app.thirdparty.extension.createpay;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayAlipayWapCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO;
import com.fushun.pay.app.thirdparty.extensionpoint.CreatePayThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.alipay.pay.AlipayWapPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日19时36分
 */
@Extension(bizCode = BizCode.CREATEPAY_ALIPAY_WAP)
public class CreatePayAliPayWapThirdPartyExt implements CreatePayThirdPartyExtPt<CreatePayAlipayWapCO> {

    private Logger logger = LoggerFactory.getLogger(CreatePayAliPayWapThirdPartyExt.class);

    @Autowired
    private AlipayWapPayFacade payFacade;

    public CreatedPayRequestBodyCO created(CreatePayAlipayWapCO payCO) {
        return payFacade.getRequest(payCO);
    }
}
