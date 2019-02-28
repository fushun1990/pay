package com.tencent.protocol.apppay_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * 微信app支付  发起支付，返回对象
 *
 * @author fushun
 * @version devlogin
 * @creation 2017年5月27日
 */
public class AppPayReqData extends BaseReqData {

    /**
     * 微信支付分配的商户号
     */
    private String partnerid;

    /**
     * 统一下单对象
     */
    private String prepayid;

    /**
     * 暂填写固定值Sign=WXPay
     */
    private String package_;

    /**
     * 时间戳	timestamp	String(10)	是	1412000000	时间戳，请见接口规则-参数规定
     */
    private String timestamp;

    /**
     * 随机字符串	noncestr	String(32)	是	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
     */
    private String noncestr;

    public AppPayReqData(Configure configure) {
        super(null, configure);
        this.noncestr = this.getNonce_str();
        this.partnerid = this.getMch_id();
        this.setMch_id(null);
        this.setSign_type(null);
        this.setNonce_str(null);
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackage_() {
        return package_;
    }

    public void setPackage_(String package_) {
        this.package_ = package_;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }


}
