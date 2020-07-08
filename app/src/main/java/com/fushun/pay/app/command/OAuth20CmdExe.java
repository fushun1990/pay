package com.fushun.pay.app.command;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.fushun.pay.app.dto.OAuth20Cmd;
import com.fushun.pay.app.dto.clientobject.oauth20.OAuth20ResponseVO;
import com.fushun.pay.app.thirdparty.extensionpoint.OAuth20ThirartExtPt;
import com.fushun.pay.app.validator.extensionpoint.OAuth20ValidatorExtPt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description 获取授权信息
 * @creation 2019年01月31日21时20分
 */
@Component
public class OAuth20CmdExe {

    @Autowired
    private ExtensionExecutor extensionExecutor;

    public SingleResponse<OAuth20ResponseVO> execute(OAuth20Cmd cmd) {

        extensionExecutor.executeVoid(OAuth20ValidatorExtPt.class, cmd.getBizScenario(), validator -> validator.validate(cmd));

        //获取支付信息
        OAuth20ResponseVO oAuth20ResponseVO = extensionExecutor.execute(OAuth20ThirartExtPt.class, cmd.getBizScenario(), thirdparty -> thirdparty.getOAuth20(cmd.getOAuth20CO()));

        return SingleResponse.of(oAuth20ResponseVO);
    }
}
