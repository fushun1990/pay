package com.fushun.pay.app.dto.cmd.syncresponse;

import com.fushun.pay.app.dto.PaySyncResponseCmd;
import com.fushun.pay.app.dto.clientobject.PaySyncResponseCO;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseAlipayWapCO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时09分
 */
@Data
public class PaySyncResponseAlipayWapCmd extends PaySyncResponseCmd {

    private PaySyncResponseAlipayWapCO paySyncResponseAlipayWapCO;

    @Override
    public PaySyncResponseCO getPaySyncResponseCO() {
        return paySyncResponseAlipayWapCO;
    }
}
