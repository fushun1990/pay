package com.tencent.protocol.downloadbill_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * User: rizenguo
 * Date: 2014/10/25
 * Time: 16:48
 */
public class DownloadBillReqData extends BaseReqData {

    private String bill_date = "";
    private String bill_type = "";

    /**
     * 请求对账单下载服务
     *
     * @param deviceInfo 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
     * @param billDate   下载对账单的日期，格式：yyyyMMdd 例如：20140603
     * @param billType   账单类型
     *                   ALL，返回当日所有订单信息，默认值
     *                   SUCCESS，返回当日成功支付的订单
     *                   REFUND，返回当日退款订单
     *                   REVOKED，已撤销的订单
     */


    public DownloadBillReqData(String deviceInfo, Configure configure) {
        super(deviceInfo, configure);
    }


    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

}
