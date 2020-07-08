package com.fushun.pay.app.thirdparty.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.dto.clientobject.OAuth20CO;
import com.fushun.pay.dto.clientobject.oauth20.OAuth20ResponseVO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月02日23时51分
 */
public interface OAuth20ThirartExtPt<T extends OAuth20CO> extends ExtensionPointI {

    public OAuth20ResponseVO getOAuth20(T oAuth20CO);
}
