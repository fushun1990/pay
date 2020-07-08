package com.fushun.pay.dto.clientobject.createpay;

import com.fushun.framework.base.IBaseEnum;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日00时38分
 */
public enum EStatus implements IBaseEnum<Integer> {
    /**
     *
     */
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    private Integer code;

    private String desc;

    EStatus(Integer code, String desc) {
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
