package com.fushun.pay.app.thirdparty.extension.createpay;

import com.alibaba.cola.extension.Extension;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.dto.clientobject.createpay.CreatePayWeiXinGZHDTO;
import com.fushun.pay.dto.clientobject.createpay.response.CreatedPayVO;
import com.fushun.pay.app.thirdparty.extensionpoint.CreatePayThirdPartyExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import com.fushun.pay.thirdparty.weixin.pay.WeChatGZHPayFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日00时14分
 */
@Extension(bizId = BizCode.payBizId,useCase = BizCode.payUseCase,scenario = BizCode.payScenario_WEIXIN_GZH)
public class CreatePayWeixinGZHThirdPartyExt implements CreatePayThirdPartyExtPt<CreatePayWeiXinGZHDTO> {

    private Logger logger = LoggerFactory.getLogger(CreatePayWeixinGZHThirdPartyExt.class);

    @Autowired
    private WeChatGZHPayFacade weChatOfficialAccountsPayFacade;

    @Override
    public CreatedPayVO created(CreatePayWeiXinGZHDTO payCO) {
        return weChatOfficialAccountsPayFacade.getRequest(payCO);
    }
}
