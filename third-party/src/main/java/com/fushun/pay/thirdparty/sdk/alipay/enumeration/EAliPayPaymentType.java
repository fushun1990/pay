package com.fushun.pay.thirdparty.sdk.alipay.enumeration;


import com.fushun.framework.base.BaseEnum;

/**
 * 支付宝收款类型
 *
 * @author zhoup
 */
public enum EAliPayPaymentType implements BaseEnum<String> {
    ProductPurchase("1", "商品"),
    Donate("4", "捐赠"),
    ElectronicCardVouchers("47", "电子卡券");

    private String code;

    private String text;

    EAliPayPaymentType(String code, String text) {
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
