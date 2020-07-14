package com.fushun.pay.infrastructure.common;

public class BizCode {

    /**
     * 支付业务
     */
    public final static String payBizId="pay";

    /**
     * 创建支付
     */
    public final static String payUseCase="createpay";

    /**
     * 退款
     */
    public final static String refundUseCase="refund";

    /**
     * 微信公众号
     */
    public final static String PAY_SCENARIO_WEIXIN_GZH ="weixin_gzh";
    /**
     * 微信公众号
     */
    public final static String PAY_SCENARIO_WEIXIN_XCX ="weixin_xcx";

    /**
     * 微信app
     */
    public final static String PAY_SCENARIO_WEIXIN_APP ="weixin_app";

    /**
     * 支付宝 wap
     */
    public final static String PAY_SCENARIO_ALIPAY_WAP ="alipay_wap";
    /**
     * 支付 app
     */
    public final static String PAY_SCENARIO_ALIPAY_APP ="alipay_app";

    /**
     * 退款 支付宝
     */
    public final static String REFUND_SCENARIO_ALIPAY ="alipay";

    /**
     * 退款 微信
     */
    public final static String REFUND_SCENARIO_WEIXIN ="weixin";



    public final static String OAUTH20_WEIXIN = "oauth20.weixin";

    public final static String USER_INFO_WEIXIN="userInfo.weixin";
}
