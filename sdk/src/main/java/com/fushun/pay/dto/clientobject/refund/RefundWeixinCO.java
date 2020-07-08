package com.fushun.pay.dto.clientobject.refund;

import com.fushun.pay.dto.clientobject.RefundCO;
import com.fushun.pay.dto.enumeration.ERefundAccount;
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
     * 微信退款源 不需要填写
     * 退款源 默认第一种，第一种失败之后，自动切换第二种重试退款 任何一种成功都可以
     */
    private ERefundAccount eRefundAccount;

    /**
     * 是否自动切换 退货账户
     */
    private Boolean isAutoChangeRefundAccount=true;
}
