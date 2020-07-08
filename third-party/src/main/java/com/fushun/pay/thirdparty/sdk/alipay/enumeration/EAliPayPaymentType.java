package com.fushun.pay.thirdparty.sdk.alipay.enumeration;


import com.fushun.framework.base.IBaseEnum;

/**
 * 支付宝收款类型
 *
 * @author zhoup
 */
public enum EAliPayPaymentType implements IBaseEnum<String> {
    /**
     *
     */
    ProductPurchase("1", "商品"),
    Donate("4", "捐赠"),
    ElectronicCardVouchers("47", "电子卡券");

    private String code;

    private String desc;

    EAliPayPaymentType(String code, String desc) {
        this.code = code;
        this.desc = desc;
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

}
