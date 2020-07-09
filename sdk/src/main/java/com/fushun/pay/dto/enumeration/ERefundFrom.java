package com.fushun.pay.dto.enumeration;

/**
 * 退款源
 *
 * @author zhoup
 */
public enum ERefundFrom implements ECallBackFrom<String> {
    /**
     * 退款购买会员金额
     */
    REFUND_FROM_GZH_PROPERTY("refund_from_gzh_property", "退款购买会员金额", EPayFrom.PAY_PROPERTY);


    private String code;

    private String desc;

    /**
     * 订单号前缀
     */
    private EPayFrom ePayFrom;

    ERefundFrom(String code, String desc, EPayFrom ePayFrom) {
        this.code = code;
        this.desc = desc;
        this.ePayFrom = ePayFrom;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public EPayFrom getEPayFrom() {
        return ePayFrom;
    }
}
