package com.fushun.pay.thirdparty.sdk.alipay.enumeration;

import com.fushun.framework.base.IBaseEnum;

/**
 * 支付宝支 交易状态
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月2日
 */
public enum ETradeStatus implements IBaseEnum<String> {
    /**
     * 交易创建，等待买家付款
     */
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "（交易创建，等待买家付款）"),
    /**
     * （未付款交易超时关闭，或支付完成后全额退款）
     */
    TRADE_CLOSED("TRADE_CLOSED", "（未付款交易超时关闭，或支付完成后全额退款）"),
    /**
     * （交易支付成功）
     */
    TRADE_SUCCESS("TRADE_SUCCESS", "（交易支付成功）"),
    /**
     * 交易结束，不可退款
     */
    TRADE_FINISHED("TRADE_FINISHED", "（交易结束，不可退款）");

    private String code;
    private String desc;


    private ETradeStatus(String code, String desc) {
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
}
