package com.fushun.pay.client.dto.enumeration;

import com.fushun.framework.base.IBaseEnum;

/**
 * 退款支付状态
 *
 * @author zhoup
 */
public enum ERefundStatus implements IBaseEnum<Integer> {
    /**
     *
     */
    WAIT(1, "等待退款 "),
    SUCCESS(2, "退款成功"),
    FAIL(3, "退款失败");

    private Integer code;

    private String desc;

    ERefundStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }



    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }

}
