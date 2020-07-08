package com.fushun.pay.app.dto;

import com.alibaba.cola.dto.Command;
import com.fushun.pay.dto.clientobject.PaySyncResponseCO;

/**
 * @version 1.0
 * @description 支付同步返回信息 校验
 * author wangfushun
 * @creation 2019年01月18日23时22分
 */
public abstract class PaySyncResponseCmd extends Command {

    public abstract PaySyncResponseCO getPaySyncResponseCO();
}
