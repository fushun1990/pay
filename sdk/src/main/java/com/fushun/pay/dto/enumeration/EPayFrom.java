package com.fushun.pay.dto.enumeration;

/**
 * 支付源
 *
 * @author zhoup
 */
public enum EPayFrom implements ECallBackFrom<String> {
    /**
     * 物业缴费
     */
    PAY_PROPERTY("pay_property", "物业缴费", "PPT_");

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
