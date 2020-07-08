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

    private String desc;
    /**
     * 支付单号前缀
     */
    private String preStr;


    EPayFrom(String code, String desc, String pre) {
        this.code = code;
        this.desc = desc;
        this.preStr = pre;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }

    public String getPreStr() {
        return preStr;
    }

    public void setPreStr(String preStr) {
        this.preStr = preStr;
    }

}
