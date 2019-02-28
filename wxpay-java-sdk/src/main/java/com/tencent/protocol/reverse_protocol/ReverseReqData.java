package com.tencent.protocol.reverse_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * User: rizenguo
 * Date: 2014/10/25
 * Time: 16:42
 */
public class ReverseReqData extends BaseReqData {
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
     * 请求撤销服务
     *
     * @param transactionID
     * @param outTradeNo
     * @return API返回的XML数据
     * @throws Exception
     */

    public ReverseReqData(Configure configure) {
        super(null, configure);
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


}
