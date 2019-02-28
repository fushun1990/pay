package com.tencent.common;

/**
 * 微信api列表
 *
 * @author fushun
 * @version dev706
 * @creation 2017年5月31日
 */
public class ApiConfigure {
    /**
     * 授权地址
     */
    public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    //以下是几个API的路径：
    //1）被扫支付API
    public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

    /**
     * 统一下单
     */
    public static String UNIFIEDORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 关闭订单
     */
    public static String CLOSEORDER_API = "https://api.mch.weixin.qq.com/pay/closeorder";

    //2）订单查询接口
    public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

    //3）退款API
    public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    //4）退款查询API
    public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

    //5）撤销API
    public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

    //6）下载对账单API
    public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

    //7) 统计上报API
    public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

    public static String getPAY_API() {
        return PAY_API;
    }

    public static void setPAY_API(String pAY_API) {
        PAY_API = pAY_API;
    }

    public static String getUNIFIEDORDER_API() {
        return UNIFIEDORDER_API;
    }

    public static void setUNIFIEDORDER_API(String uNIFIEDORDER_API) {
        UNIFIEDORDER_API = uNIFIEDORDER_API;
    }

    public static String getCLOSEORDER_API() {
        return CLOSEORDER_API;
    }

    public static void setCLOSEORDER_API(String cLOSEORDER_API) {
        CLOSEORDER_API = cLOSEORDER_API;
    }

    public static String getPAY_QUERY_API() {
        return PAY_QUERY_API;
    }

    public static void setPAY_QUERY_API(String pAY_QUERY_API) {
        PAY_QUERY_API = pAY_QUERY_API;
    }

    public static String getREFUND_API() {
        return REFUND_API;
    }

    public static void setREFUND_API(String rEFUND_API) {
        REFUND_API = rEFUND_API;
    }

    public static String getREFUND_QUERY_API() {
        return REFUND_QUERY_API;
    }

    public static void setREFUND_QUERY_API(String rEFUND_QUERY_API) {
        REFUND_QUERY_API = rEFUND_QUERY_API;
    }

    public static String getREVERSE_API() {
        return REVERSE_API;
    }

    public static void setREVERSE_API(String rEVERSE_API) {
        REVERSE_API = rEVERSE_API;
    }

    public static String getDOWNLOAD_BILL_API() {
        return DOWNLOAD_BILL_API;
    }

    public static void setDOWNLOAD_BILL_API(String dOWNLOAD_BILL_API) {
        DOWNLOAD_BILL_API = dOWNLOAD_BILL_API;
    }

    public static String getREPORT_API() {
        return REPORT_API;
    }

    public static void setREPORT_API(String rEPORT_API) {
        REPORT_API = rEPORT_API;
    }

    public static String getACCESS_TOKEN_URL() {
        return ACCESS_TOKEN_URL;
    }

    public static void setACCESS_TOKEN_URL(String aCCESS_TOKEN_URL) {
        ACCESS_TOKEN_URL = aCCESS_TOKEN_URL;
    }
}
