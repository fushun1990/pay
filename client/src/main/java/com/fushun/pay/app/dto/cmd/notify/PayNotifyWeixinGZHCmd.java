package com.fushun.pay.app.dto.cmd.notify;

import com.fushun.pay.app.dto.PayNotifyCmd;
import com.fushun.pay.app.dto.clientobject.PayNotifyCO;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyWeixinGZHCO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时17分
 */
@Data
public class PayNotifyWeixinGZHCmd extends PayNotifyCmd {

    private PayNotifyWeixinGZHCO payNotifyWeixinGZHCO;

    @Override
    public PayNotifyCO getPayNotifyCO() {
        return payNotifyWeixinGZHCO;
    }
}
