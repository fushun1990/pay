package com.fushun.pay.app.dto.clientobject.refund;

import com.fushun.pay.app.dto.clientobject.RefundCO;
import com.fushun.pay.app.dto.enumeration.ERefundAccount;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日23时05分
 */
@Data
public class RefundWeixinCO extends RefundCO {
    /**
     * 微信退款源
     * 退款源
     */
    private ERefundAccount eRefundAccount;
}
