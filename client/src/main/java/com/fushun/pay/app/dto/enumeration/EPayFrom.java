package com.fushun.pay.app.dto.enumeration;

/**
 * 支付源
 *
 * @author zhoup
 */
public enum EPayFrom implements ECallBackFrom<String> {
    /**
     * 购买会员
     */
    PAY_FROM_BUY_MEMBERS("pay_from_buy_members", "公众号", "WDW_");

    private String code;

    private String text;
    /**
     * 支付单号前缀
     */
    private String preStr;


    EPayFrom(String code, String text, String pre) {
        this.code = code;
        this.text = text;
        this.preStr = pre;
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

    public String getPreStr() {
        return preStr;
    }

    public void setPreStr(String preStr) {
        this.preStr = preStr;
    }

}
