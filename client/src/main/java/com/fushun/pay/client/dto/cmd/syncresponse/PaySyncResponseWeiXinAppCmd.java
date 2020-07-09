package com.fushun.pay.client.dto.cmd.syncresponse;

import com.fushun.pay.client.dto.PaySyncResponseCmd;
import com.fushun.pay.dto.clientobject.PaySyncResponseValidatorDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseWeixinAppValidatorDTO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时09分
 */
@Data
public class PaySyncResponseWeiXinAppCmd extends PaySyncResponseCmd {

    private PaySyncResponseWeixinAppValidatorDTO paySyncResponseWeixinAppCO;

    @Override
    public PaySyncResponseValidatorDTO getPaySyncResponseValidatorTO() {
        return paySyncResponseWeixinAppCO;
    }
}
