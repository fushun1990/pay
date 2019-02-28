package com.tencent.protocol.refund_query_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * User: rizenguo
 * Date: 2014/10/25
 * Time: 16:35
 */
public class RefundQueryReqData extends BaseReqData {
    //每个字段具体的意思请查看API文档

    /**
     * 是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。建议优先使用
     */
    private String transaction_id = "";
    /**
     * 商户系统内部的订单号,transaction_id 、out_trade_no 二选一，如果同时存在优先级：transaction_id>out_trade_no
     */
    private String out_trade_no = "";
    private String out_refund_no;
    private String refund_id;

    /**
     * 请求退款查询服务
     *
     * @param deviceInfo 微信支付分配的终端设备号，与下单一致
     */
    public RefundQueryReqData(String deviceInfo, Configure configure) {
        super(deviceInfo, configure);
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }


}
