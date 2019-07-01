package com.fushun.pay.app.dto.enumeration;

/**
 * 退款源
 *
 * @author zhoup
 */
public enum ERefundFrom implements ECallBackFrom<String> {
    /**
     * 退款购买会员金额
     */
    REFUND_FROM_GZH_BUY_MEMBERS("refund_from_gzh_buy_members", "退款购买会员金额", EPayFrom.PAY_FROM_BUY_MEMBERS);


    private String code;

    private String text;

    /**
     * 订单号前缀
     */
    private EPayFrom ePayFrom;

    ERefundFrom(String code, String text, EPayFrom ePayFrom) {
        this.code = code;
        this.text = text;
        this.ePayFrom = ePayFrom;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }

    public EPayFrom getEPayFrom() {
        return ePayFrom;
    }
}
