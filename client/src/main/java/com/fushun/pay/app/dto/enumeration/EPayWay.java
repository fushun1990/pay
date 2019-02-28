package com.fushun.pay.app.dto.enumeration;

import com.fushun.framework.util.exception.base.BaseEnum;

/**
 * 支付信息状态
 *
 * @author zhoup
 */
public enum EPayWay implements BaseEnum<String> {
    /**
     * 支付宝
     */
    PAY_WAY_ALIPAY("pay_way_alipay", "支付宝"),
    /**
     * 银联
     */
    PAY_WAY_UNIONPAY("pay_way_unionpay", "银联"),
    /**
     * 微信
     */
    PAY_WAY_WEIXINPAY("pay_way_weixinpay", "微信"),
    /**
     * 微信appC支付
     */
    PAY_WAY_APPC_WEIXINPAY("pay_way_appc_weixinpay", "微信App支付");


    private String code;

    private String text;

    EPayWay(String code, String text) {
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
