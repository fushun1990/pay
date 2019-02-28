package com.fushun.pay.app.thirdparty.extension.createpay;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayAlipayAppCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO;
import com.fushun.pay.app.thirdparty.extensionpoint.CreatePayThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.alipay.pay.AlipayAppPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日19时36分
 */
@Extension(bizCode = BizCode.CREATEPAY_ALIPAY_APP)
public class CreatePayAliPayAppThirdPartyExt implements CreatePayThirdPartyExtPt<CreatePayAlipayAppCO> {

    private Logger logger = LoggerFactory.getLogger(CreatePayAliPayAppThirdPartyExt.class);

    @Autowired
    private AlipayAppPayFacade payFacade;

    public CreatedPayRequestBodyCO created(CreatePayAlipayAppCO payCO) {
        return payFacade.getRequest(payCO);
    }
}
