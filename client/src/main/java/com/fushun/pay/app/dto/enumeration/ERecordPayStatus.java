package com.fushun.pay.app.dto.enumeration;

import com.fushun.framework.base.IBaseEnum;

/**
 * 支付信息状态
 *
 * @author zhoup
 */
public enum ERecordPayStatus implements IBaseEnum<Integer> {
    /**
     *
     */
    SUCCESS(1, "成功"),
    FAILED(2, "失败"),
    CREATED(3, "创建");

    private Integer code;

    private String desc;

    ERecordPayStatus(Integer code, String desc) {
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
