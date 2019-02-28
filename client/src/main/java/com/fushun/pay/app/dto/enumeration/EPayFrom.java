package com.fushun.pay.app.dto.enumeration;

/**
 * 支付源
 *
 * @author zhoup
 */
public enum EPayFrom implements ECallBackFrom<String> {
    /**
     * Web端下载视频
     */
    PAY_FROM_WEB_DOWN_VIDEO("pay_from_web_down_video", "Web端", "WDW_"),
    /**
     * APP端
     */
    PAY_FROM_APP_DOWN_VIDEO("pay_from_App_down_video", "APP端", "ADW_"),
    /**
     * APP端
     */
    PAY_FROM_APP_BUY_GOODS("pay_from_App_buy_goods", "APP端", "ABG_");

    private String code;

    private String text;
    /**
     * 支付单号前缀
     */
    private String preStr;


    EPayFrom(String code, String text, String pre) {
        this.code = code;
        this.text = text;
        this.preStr = pre;
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
