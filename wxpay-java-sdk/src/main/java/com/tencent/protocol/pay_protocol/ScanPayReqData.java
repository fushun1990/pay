package com.tencent.protocol.pay_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * 请求被扫支付API需要提交的数据
 */
public class ScanPayReqData extends BaseReqData {

    //每个字段具体的意思请查看API文档
    /**
     * 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
     */
    private String body = "";
    /**
     * 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回
     */
    private String attach = "";
    /**
     * 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
     */
    private String out_trade_no = "";
    /**
     * 订单总金额，单位为“分”，只能整数
     */
    private int total_fee = 0;
    /**
     * 订单生成的机器IP
     */
    private String spbill_create_ip = "";
    /**
     * 订单生成时间， 格式为yyyyMMddHHmmss，如2009年12 月25 日9 点10 分10 秒表示为20091225091010。时区为GMT+8 beijing。该时间取自商户服务器
     */
    private String time_start = "";
    /**
     * 订单失效时间，格式同上
     */
    private String time_expire = "";
    /**
     * 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
     */
    private String goods_tag = "";
    /**
     * 这个是扫码终端设备从用户手机上扫取到的支付授权号，这个号是跟用户用来支付的银行卡绑定的，有效期是1分钟
     */
    private String auth_code = "";

    /**
     * @param deviceInfo 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
     */
    public ScanPayReqData(String deviceInfo, Configure configure) {
        super(deviceInfo, configure);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }


}
