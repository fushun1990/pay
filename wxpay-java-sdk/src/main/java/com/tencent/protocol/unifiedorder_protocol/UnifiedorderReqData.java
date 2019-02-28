package com.tencent.protocol.unifiedorder_protocol;

import com.tencent.common.Configure;

/**
 * 公众号统一下单对象
 *
 * @author fushun
 * @version devlogin
 * @creation 2017年5月26日
 */
public class UnifiedorderReqData extends AppUnifiedOrderReqData {

    /**
     * 用户标识	openid	否	String(128)	oUpF8uMuAJO_M2pxb1Q9zNjWeS6o	trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     */
    private String openid;

    public UnifiedorderReqData(String device_info, Configure configure) {
        super(device_info, configure);
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
