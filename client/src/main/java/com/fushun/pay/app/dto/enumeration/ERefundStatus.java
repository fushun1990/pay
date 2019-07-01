package com.fushun.pay.app.dto.enumeration;

import com.fushun.framework.base.BaseEnum;

/**
 * 退款支付状态
 *
 * @author zhoup
 */
public enum ERefundStatus implements BaseEnum<Integer> {
    wait(1, "等待退款 "),
    success(2, "退款成功"),
    fail(3, "退款失败");

    private Integer code;

    private String text;

    ERefundStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
