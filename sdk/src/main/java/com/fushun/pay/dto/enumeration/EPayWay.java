package com.fushun.pay.dto.enumeration;


import com.fushun.framework.base.IBaseEnum;

/**
 * 支付信息状态
 *
 * @author zhoup
 */
public enum EPayWay implements IBaseEnum<String> {
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

    private String desc;

    EPayWay(String code, String desc) {
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

    public void setText(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }

}
