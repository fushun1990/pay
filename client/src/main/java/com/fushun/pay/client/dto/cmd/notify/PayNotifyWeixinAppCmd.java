package com.fushun.pay.client.dto.cmd.notify;

import com.fushun.pay.client.dto.PayNotifyCmd;
import com.fushun.pay.dto.clientobject.PayNotifyDTO;
import com.fushun.pay.dto.clientobject.notify.PayNotifyWeixinAppDTO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时17分
 */
@Data
public class PayNotifyWeixinAppCmd extends PayNotifyCmd {

    private PayNotifyWeixinAppDTO payNotifyWeixinAppCO;

    @Override
    public PayNotifyDTO getPayNotifyDTO() {
        return payNotifyWeixinAppCO;
    }
}
