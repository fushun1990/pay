package com.fushun.pay.app.dto.cmd.refund;

import com.fushun.pay.app.dto.RefundCmd;
import com.fushun.pay.app.dto.clientobject.RefundCO;
import com.fushun.pay.app.dto.clientobject.refund.RefundWeixinCO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月31日21时31分
 */
@Data
public class RefundWeixinCmd extends RefundCmd {

    private RefundWeixinCO refundWeixinCO;

    @Override
    public RefundCO getRefundCO() {
        return this.refundWeixinCO;
    }
}
