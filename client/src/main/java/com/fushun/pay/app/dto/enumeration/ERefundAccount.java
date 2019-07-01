package com.fushun.pay.app.dto.enumeration;


import com.fushun.framework.base.BaseEnum;

/**
 * 微信退款源
 *
 * @author fushun
 * @version VS1.3
 * @creation 2017年7月4日
 */
public enum ERefundAccount implements BaseEnum<String> {

    REFUND_SOURCE_UNSETTLED_FUNDS("REFUND_SOURCE_UNSETTLED_FUNDS", "未结算资金退款（默认使用未结算资金退款）"),
    REFUND_SOURCE_RECHARGE_FUNDS("REFUND_SOURCE_RECHARGE_FUNDS", "可用余额退款");

    private String code;

    private String text;

    ERefundAccount(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }


}
