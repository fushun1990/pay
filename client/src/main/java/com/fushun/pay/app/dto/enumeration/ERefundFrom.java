package com.fushun.pay.app.dto.enumeration;

/**
 * 支付信息状态
 *
 * @author zhoup
 */
public enum ERefundFrom implements ECallBackFrom<String> {
    /**
     * Web端下载视频
     */
    REFUND_FROM_WEB_DOWN_VIDEO("refund_from_web_down_video", "Web端", "WDW_"),
    /**
     * APP端
     */
    REFUND_FROM_APP_DOWN_VIDEO("refund_from_App_down_video", "APP端", "ADW_"),
    /**
     * APP端
     */
    REFUND_FROM_APP_BUY_GOODS("refund_from_App_buy_goods", "APP端", "ABG_");


    private String code;

    private String text;

    /**
     * 订单号前缀
     */
    private String preStr;

    ERefundFrom(String code, String text, String preStr) {
        this.code = code;
        this.text = text;
        this.preStr = preStr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public String getPreStr() {
        return preStr;
    }

    public void setPreStr(String preStr) {
        this.preStr = preStr;
    }

}
