package com.fushun.pay.client.dto.cmd.createdpay;

import com.fushun.pay.client.dto.CreatePayCmd;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayAppDTO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时31分
 */
@Data
public class CreatePayAlipayAppCmd extends CreatePayCmd {

    private CreatePayAlipayAppDTO createPayAlipayAppDTO;

    @Override
    public PayDTO getPayDTO() {
        return this.createPayAlipayAppDTO;
    }
}