package com.fushun.pay.app.dto;

import com.alibaba.cola.dto.Command;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;

/**
 * @version 1.0
 * @description 支付异步通知
 * author wangfushun
 * @creation 2019年01月18日23时22分
 */
public abstract class PayNotifyCmd extends Command {

    public abstract PayNotifyCO getPayNotifyCO();
}
