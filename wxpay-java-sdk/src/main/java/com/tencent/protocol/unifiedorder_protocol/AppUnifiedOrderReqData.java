package com.tencent.protocol.unifiedorder_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * app 统一下单对象
 *
 * @author fushun
 * @version devlogin
 * @creation 2017年5月26日
 */
public class AppUnifiedOrderReqData extends BaseReqData {

    /**
     * 商品描述	body	是	String(128)	腾讯充值中心-QQ会员充值	商品简单描述，该字段请按照规范传递，具体请见参数规定
     */
    private String body;
    /**
     * 商品详情	detail	否	String(6000)
     * {
     * "cost_price":608800,
     * "receipt_id":"wx123",
     * "goods_detail":[
     * {
     * "goods_id":"商品编码",
     * "wxpay_goods_id":"1001",
     * "goods_name":"iPhone6s 16G",
     * "quantity":1,
     * "price":528800
     * },
     * {
     * "goods_id":"商品编码",
     * "wxpay_goods_id":"1002",
     * "goods_name":"iPhone6s 32G",
     * "quantity":1,
     * "price":608800
     * }
     * ]
     * }
     * <p>
     * 商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
     * cost_price Int 可选 32 订单原价，商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的支付金额。当订单原价与支付金额不相等则被判定为拆单，无法享受优惠。
     * receipt_id String 可选 32 商家小票ID
     * goods_detail 服务商必填 []：
     * └ goods_id String 必填 32 商品的编号
     * └ wxpay_goods_id String 可选 32 微信支付定义的统一商品编号
     * └ goods_name String 可选 256 商品名称
     * └ quantity Int 必填  32 商品数量
     * └ price Int 必填 32 商品单价，如果商户有优惠，需传输商户优惠后的单价
     * 注意：单品总金额应<=订单总金额total_fee，否则会无法享受优惠。
     */
    private String detail;
    /**
     * 附加数据	attach	否	String(127)	深圳分店	附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     */
    private String attach;
    /**
     * 商户订单号	out_trade_no	是	String(32)	20150806125346	商户系统内部订单号，要求32个字符内、且在同一个商户号下唯一。 详见商户订单号
     */
    private String out_trade_no;
    /**
     * 标价币种	fee_type	否	String(16)	CNY	符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
     */
    private String fee_type;
    /**
     * 标价金额	total_fee	是	Int	88	订单总金额，单位为分，详见支付金额
     */
    private Integer total_fee;
    /**
     * 终端IP	spbill_create_ip	是	String(16)	123.12.12.123	APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
     */
    private String spbill_create_ip;
    /**
     * 交易起始时间	time_start	否	String(14)	20091225091010	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     */
    private String time_start;
    /**
     * 交易结束时间	time_expire	否	String(14)	20091227091010
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
     * 注意：最短失效时间间隔必须大于5分钟
     */
    private String time_expire;
    /**
     * 商品标记	goods_tag	否	String(32)	WXG	商品标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
     */
    private String goods_tag;
    /**
     * 通知地址	notify_url	是	String(256)	http://www.weixin.qq.com/wxpay/pay.php	异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    private String notify_url;
    /**
     * 交易类型	trade_type	是	String(16)	JSAPI	取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
     * <p>
     * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里 MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
     */
    private String trade_type;
    /**
     * 商品ID	product_id	否	String(32)	12235413214070356458058	trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     */
    private String product_id;
    /**
     * 指定支付方式	limit_pay	否	String(32)	no_credit	上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    private String limit_pay;

    public AppUnifiedOrderReqData(String device_info, Configure configure) {
        super(device_info, configure);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
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

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

}
