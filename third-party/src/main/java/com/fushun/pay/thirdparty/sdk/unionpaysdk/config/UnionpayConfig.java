package com.fushun.pay.thirdparty.sdk.unionpaysdk.config;

public class UnionpayConfig {
    /**
     * 银联支付版本
     */
    public static final String UNIONPAY_VERSION = "5.0.0";

    /**
     * 网页字符集编码
     */
    public static final String ENCODING_UTF8 = "UTF-8";

    /**
     * 银联商户号，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
     */
    public static final String UNIONPAY_MERID = "802500048160528";

    /**
     * 银联签名方法，只支持 01：RSA方式证书加密
     */
    public static final String UNIONPAY_SIGN_METHOD_RSA = "01";

    /**
     * 银联交易类型 ，01：消费
     */
    public static final String UNIONPAY_TXN_TYPE_CONSUME = "01";
    /**
     * 银联交易子类型 ，01：自助消费
     */
    public static final String UNIONPAY_TXN_TYPE_BUFFET_CONSUME = "01";
    /**
     * 银联业务类型 ，B2C网关支付，手机wap支付
     */
    public static final String UNIONPAY_BIZ_TYPE = "000201";
    /**
     * 银联渠道类型 ，这个字段区分B2C网关支付和手机wap支付；07：PC,平板;08：手机
     */
    public static final String UNIONPAY_CHANNEL_TYPE = "07";
    /**
     * 银联 接入类型，0：直连商户
     */
    public static final String UNIONPAY_ACCESS_TYPE = "0";

    /**
     * 交易币种（境内商户一般是156  人民币）
     */
    public static final String UNIONPAY_CURRENCY_CODE = "156";
}
