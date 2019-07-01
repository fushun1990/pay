package com.fushun.pay.app.dto.clientobject.createpay;

import com.fushun.framework.base.BaseEnum;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日00时38分
 */
public enum EStatus implements BaseEnum<Integer> {
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    private Integer code;

    private String text;

    EStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }


    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }
}
