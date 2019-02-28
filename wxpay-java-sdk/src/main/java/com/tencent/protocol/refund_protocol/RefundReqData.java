package com.tencent.protocol.refund_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * User: rizenguo
 * Date: 2014/10/25
 * Time: 16:12
 */
public class RefundReqData extends BaseReqData {

    //每个字段具体的意思请查看API文档
    /**
     * 是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。建议优先使用
     */
    private String transaction_id = "";
    /**
     * 商户系统内部的订单号,transaction_id 、out_trade_no 二选一，如果同时存在优先级：transaction_id>out_trade_no
     */
    private String out_trade_no = "";
    /**
     * 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
     */
    private String out_refund_no = "";
    /**
     * 订单总金额，单位为分
     */
    private int total_fee = 0;
    /**
     * 退款总金额，单位为分,可以做部分退款
     */
    private int refund_fee = 0;
    /**
     * 货币类型，符合ISO 4217标准的三位字母代码，默认为CNY（人民币）
     */
    private String refund_fee_type = "CNY";
    /**
     * 操作员帐号, 默认为商户号
     */
    private String op_user_id = "";

    /**
     * 微信 退款源
     */
    private String refund_account;

    /**
     * 退款原因
     */
    private String refund_desc;

    /**
     * 请求退款服务
     *
     * @param deviceInfo 微信支付分配的终端设备号，与下单一致
     */
    public RefundReqData(String deviceInfo, Configure configure) {
        super(deviceInfo, configure);
        this.op_user_id = this.getMch_id();
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

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public int getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(int refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(String op_user_id) {
        this.op_user_id = op_user_id;
    }

    public String getRefund_fee_type() {
        return refund_fee_type;
    }

    public void setRefund_fee_type(String refund_fee_type) {
        this.refund_fee_type = refund_fee_type;
    }

    public String getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(String refund_account) {
        this.refund_account = refund_account;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public void setRefund_desc(String refund_desc) {
        this.refund_desc = refund_desc;
    }
}
