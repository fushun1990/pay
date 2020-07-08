package com.fushun.pay.app.dto.cmd.createdpay;

import com.fushun.pay.app.dto.CreatePayCmd;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayWapDTO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时31分
 */
@Data
public class CreatePayAlipayWapCmd extends CreatePayCmd {

    private CreatePayAlipayWapDTO createPayAlipayWapDTO;

    @Override
    public PayDTO getPayCO() {
        return this.createPayAlipayWapDTO;
    }
}
