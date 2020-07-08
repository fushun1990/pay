package com.fushun.pay.app.dto.enumeration;


import com.fushun.framework.base.IBaseEnum;

/**
 * 退款 通知状态
 *
 * @author zhoup
 */
public enum ERefundNotityStatus implements IBaseEnum<Integer> {
    /**
     *
     */
    YES(1, "已通知 "),
    NO(2, "未通知");

    private Integer code;

    private String desc;

    ERefundNotityStatus(Integer code, String desc) {
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
