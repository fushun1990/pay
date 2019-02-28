package com.fushun.pay.app.dto.cmd.notify;

import com.fushun.pay.app.dto.PayNotifyCmd;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyAlipayAppCO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时17分
 */
@Data
public class PayNotifyAlipayAppCmd extends PayNotifyCmd {

    private PayNotifyAlipayAppCO payAlipayAppNotifyCO;

    @Override
    public PayNotifyCO getPayNotifyCO() {
        return payAlipayAppNotifyCO;
    }
}
