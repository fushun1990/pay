package com.tencent.protocol.base_protocol;

import com.tencent.common.Configure;
import com.tencent.common.RandomStringGenerator;

public class BaseReqData {
    /**
     * 公众账号ID
     */
    private String appid = "";
    /**
     * 商户号
     */
    private String mch_id = "";
    /**
     * 设备号
     */
    private String device_info = "";
    /**
     * 随机字符串
     */
    private String nonce_str = "";
    /**
     * 签名
     */
    private String sign = "";
    /**
     * 签名类型
     */
    private String sign_type = "MD5";

    /**
     * 配置对象
     */
    private Configure configure;

    public BaseReqData(String device_info, Configure configure) {
        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(configure.getAppID());

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(configure.getMchID());


        //商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
        setDevice_info(device_info);

        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        this.configure = configure;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public Configure getConfigure() {
        return configure;
    }

}
