package com.fushun.pay.app.dto.cmd.syncresponse;

import com.fushun.pay.app.dto.PaySyncResponseCmd;
import com.fushun.pay.dto.clientobject.PaySyncResponseCO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseAlipayAppCO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时09分
 */
public class PaySyncResponseAlipayAppCmd extends PaySyncResponseCmd {

    private PaySyncResponseAlipayAppCO syncResponseAlipayAppCO;

    @Override
    public PaySyncResponseCO getPaySyncResponseCO() {
        return syncResponseAlipayAppCO;
    }
}
