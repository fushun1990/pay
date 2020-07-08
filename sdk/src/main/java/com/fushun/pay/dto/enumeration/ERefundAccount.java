package com.fushun.pay.dto.enumeration;


import com.fushun.framework.base.IBaseEnum;

/**
 * 微信退款源
 *
 * @author fushun
 * @version VS1.3
 * @creation 2017年7月4日
 */
public enum ERefundAccount implements IBaseEnum<String> {
    /**
     *
     */
    REFUND_SOURCE_UNSETTLED_FUNDS("REFUND_SOURCE_UNSETTLED_FUNDS", "未结算资金退款（默认使用未结算资金退款）"),
    REFUND_SOURCE_RECHARGE_FUNDS("REFUND_SOURCE_RECHARGE_FUNDS", "可用余额退款");

    private String code;

    private String desc;

    ERefundAccount(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }



    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }

}
