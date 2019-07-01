package com.fushun.pay.app.dto.enumeration;


import com.fushun.framework.base.BaseEnum;

/**
 * 支付系统通知状态
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2016年9月17日
 */
public enum ERecordPayNotityStatus implements BaseEnum<Integer> {
    yes(1, "已通知 "),
    no(2, "未通知");

    private Integer code;

    private String text;

    ERecordPayNotityStatus(Integer code, String text) {
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
