package com.fushun.pay.app.validator.extension.oauth20;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.extension.Extension;
import com.fushun.pay.app.common.exception.ErrorCode;
import com.fushun.pay.app.dto.clientobject.oauth20.WeixinOauth20CO;
import com.fushun.pay.app.dto.cmd.oauth20.Oauth20WeixinCmd;
import com.fushun.pay.app.validator.extensionpoint.OAuth20ValidatorExtPt;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.util.StringUtils;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月31日21时36分
 */
@Extension(bizCode = BizCode.OAUTH20_WEIXIN)
public class WeixinOAuth20ValidatorExt implements OAuth20ValidatorExtPt<Oauth20WeixinCmd> {

    @Override
    public void validate(Oauth20WeixinCmd candidate) {
        WeixinOauth20CO weixinOauth20CO = candidate.getOAuth20CO();

        if (StringUtils.isEmpty(weixinOauth20CO.getCode())) {
            throw new BizException(ErrorCode.OAUTH20_FAIL, ErrorCode.OAUTH20_FAIL.getErrDesc());
        }
    }
}
