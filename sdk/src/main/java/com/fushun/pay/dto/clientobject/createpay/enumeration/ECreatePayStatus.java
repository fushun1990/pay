package com.fushun.pay.dto.clientobject.createpay.enumeration;

import com.fushun.framework.base.IBaseEnum;

/**
 * 支付信息状态
 *
 * @author zhoup
 */
public enum ECreatePayStatus implements IBaseEnum<Integer> {
    /**
     *
     */
    SUCCESS(1, "创建成功"),
    FAIL(2, "失败"),
    HAS_PAY_SUCCESS(3, "已支付成功");

    private Integer code;

    private String desc;

    ECreatePayStatus(Integer code, String desc) {
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
