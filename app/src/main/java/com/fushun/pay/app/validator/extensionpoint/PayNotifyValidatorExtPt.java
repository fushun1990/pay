package com.fushun.pay.app.validator.extensionpoint;

import com.alibaba.cola.extension.ExtensionPointI;
import com.alibaba.cola.validator.ValidatorI;
import com.fushun.pay.app.dto.PayNotifyCmd;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付 异步通知 验证扩展
 * @creation 2019年01月18日22时23分
 */
public interface PayNotifyValidatorExtPt<T extends PayNotifyCmd> extends ValidatorI<T>, ExtensionPointI {

}
