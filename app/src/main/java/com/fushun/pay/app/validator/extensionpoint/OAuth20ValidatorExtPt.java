package com.fushun.pay.app.validator.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.fushun.pay.app.dto.OAuth20Cmd;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月31日21时35分
 */
public interface OAuth20ValidatorExtPt<T extends OAuth20Cmd> extends ExtensionPointI {

    public void validate(T candidate);
}
