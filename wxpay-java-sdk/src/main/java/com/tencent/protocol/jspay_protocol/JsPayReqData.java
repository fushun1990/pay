package com.tencent.protocol.jspay_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * 公众号支付 参数对象
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月4日
 */
public class JsPayReqData extends BaseReqData {

    /**
     * 公众账号ID
     */
    private String appId = "";
    /**
     * 随机字符串
     */
    private String nonceStr = "";

    /**
     * 签名类型
     */
    private String signType = "MD5";
    /**
     * 订单详情扩展字符串	package	是	String(128)	prepay_id=123456789	统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
     */
    private String package_;
    /**
     * 时间戳	timeStamp	是	String(32)	1414561699	当前的时间，其他详见时间戳规则
     */
    private String timeStamp;

    public JsPayReqData(Configure configure) {
        super(null, configure);
        this.setAppId(this.getAppid());
        this.setSignType(this.getSign_type());
        this.setNonceStr(this.getNonce_str());
        this.setAppid(null);
        this.setMch_id(null);
        this.setSign_type(null);
        this.setNonce_str(null);
    }

    public String getPackage_() {
        return package_;
    }

    public void setPackage_(String package_) {
        this.package_ = package_;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

}
