package com.fushun.pay.app.dto.cmd.syncresponse;

import com.fushun.pay.app.dto.PaySyncResponseCmd;
import com.fushun.pay.app.dto.clientobject.PaySyncResponseCO;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHCO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时09分
 */
@Data
public class PaySyncResponseWeiXinGZHCmd extends PaySyncResponseCmd {

    private PaySyncResponseWeixinGZHCO paySyncResponseWeixinGZHCO;

    @Override
    public PaySyncResponseCO getPaySyncResponseCO() {
        return paySyncResponseWeixinGZHCO;
    }
}
