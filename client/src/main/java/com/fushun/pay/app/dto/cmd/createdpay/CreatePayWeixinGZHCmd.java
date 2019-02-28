package com.fushun.pay.app.dto.cmd.createdpay;

import com.fushun.pay.app.dto.CreatePayCmd;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayWeiXinGZHCO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时31分
 */
@Data
public class CreatePayWeixinGZHCmd extends CreatePayCmd {

    private CreatePayWeiXinGZHCO createPayWeiXinGZHCO;

    @Override
    public PayCO getPayCO() {
        return this.createPayWeiXinGZHCO;
    }
}
