package com.fushun.pay.app.dto.cmd.notify;

import com.fushun.pay.app.dto.PayNotifyCmd;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyAlipayWapCO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时17分
 */
@Data
public class PayNotifyAlipayWapCmd extends PayNotifyCmd {

    private PayNotifyAlipayWapCO payNotifyAlipayWapCO;

    @Override
    public PayNotifyCO getPayNotifyCO() {
        return payNotifyAlipayWapCO;
    }
}
