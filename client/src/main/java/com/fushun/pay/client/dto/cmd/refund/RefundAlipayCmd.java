package com.fushun.pay.client.dto.cmd.refund;

import com.fushun.pay.client.dto.RefundCmd;
import com.fushun.pay.dto.clientobject.RefundCO;
import com.fushun.pay.dto.clientobject.refund.RefundAlipayCO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月31日21时31分
 */
@Data
public class RefundAlipayCmd extends RefundCmd {

    private RefundAlipayCO refundAlipayCO;

    @Override
    public RefundCO getRefundCO() {
        return this.refundAlipayCO;
    }
}
