package com.tencent.common;

import java.net.URL;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
public abstract class Configure {
//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改


    protected String key = "";

    //微信分配的公众号ID（开通公众号之后可以获取到）
    protected String appID = "";
    /**
     * 微信公众好 app开发秘钥
     */
    protected String appSecret = "";

    //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    protected String mchID = "";

    //受理模式下给子商户分配的子商户号
    protected String subMchID = "";

    //HTTPS证书的本地路径
    protected URL certLocalPath = null;

    //HTTPS证书密码，默认密码等于商户号MCHID
    protected String certPassword = "";

    //是否使用异步线程的方式来上报API测速，默认为异步模式
    protected boolean useThreadToDoReport = true;

    //机器IP
    protected String ip = "";

    protected String HttpsRequestClassName = "com.tencent.common.HttpsRequest";

    /**
     * 获取单利对象
     *
     * @return
     * @author fushun
     * @version dev706
     * @creation 2017年5月31日
     * @records <p>  fushun 2017年5月31日</p>
     */
    public abstract Configure getConfigure();

    public String getKey() {
        return getConfigure().key;
    }

    public void setKey(String key) {
        getConfigure().key = key;
    }

    public String getAppID() {
        return getConfigure().appID;
    }

    public void setAppID(String appID) {
        getConfigure().appID = appID;
    }

    public String getAppSecret() {
        return getConfigure().appSecret;
    }

    public void setAppSecret(String appSecret) {
        getConfigure().appSecret = appSecret;
    }

    public String getMchID() {
        return getConfigure().mchID;
    }

    public void setMchID(String mchID) {
        getConfigure().mchID = mchID;
    }

    public String getSubMchID() {
        return getConfigure().subMchID;
    }

    public void setSubMchID(String subMchID) {
        getConfigure().subMchID = subMchID;
    }

    public URL getCertLocalPath() {
        return getConfigure().certLocalPath;
    }

    public void setCertLocalPath(URL certLocalPath) {
        getConfigure().certLocalPath = certLocalPath;
    }

    public String getCertPassword() {
        return getConfigure().certPassword;
    }

    public void setCertPassword(String certPassword) {
        getConfigure().certPassword = certPassword;
    }

    public boolean isUseThreadToDoReport() {
        return getConfigure().useThreadToDoReport;
    }

    public void setUseThreadToDoReport(boolean useThreadToDoReport) {
        getConfigure().useThreadToDoReport = useThreadToDoReport;
    }

    public String getIp() {
        return getConfigure().ip;
    }

    public void setIp(String ip) {
        getConfigure().ip = ip;
    }

    public String getHttpsRequestClassName() {
        return HttpsRequestClassName;
    }

    public void setHttpsRequestClassName(String httpsRequestClassName) {
        getConfigure().HttpsRequestClassName = httpsRequestClassName;
    }

}
