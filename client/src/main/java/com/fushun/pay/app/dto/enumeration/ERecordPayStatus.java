package com.fushun.pay.app.dto.enumeration;

import com.fushun.framework.util.exception.base.BaseEnum;

/**
 * 支付信息状态
 *
 * @author zhoup
 */
public enum ERecordPayStatus implements BaseEnum<Integer> {
    success(1, "成功"),
    failed(2, "失败"),
    created(3, "创建");

    private Integer code;

    private String text;

    ERecordPayStatus(Integer code, String text) {
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
