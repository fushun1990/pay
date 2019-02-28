package com.tencent.protocol.report_protocol;

import com.tencent.common.Configure;
import com.tencent.protocol.base_protocol.BaseReqData;

/**
 * User: rizenguo
 * Date: 2014/11/12
 * Time: 17:05
 */
public class ReportReqData extends BaseReqData {


    /**
     * 报对应的接口的完整URL，类似：https://api.mch.weixin.qq.com/pay/unifiedorder
     */
    private String interface_url;
    /**
     * 接口耗时情况，单位为毫秒
     */
    private int execute_time_cost;
    /**
     * 发起接口调用时的机器IP
     */
    private String user_ip;
    /**
     * 上报该统计请求时的系统时间，格式为yyyyMMddHHmmss
     */
    private String time;

    //以下是API接口返回的对应数据
    private String return_code;
    private String return_msg;
    private String result_code;
    private String err_code;
    private String err_code_des;
    private String out_trade_no;

    public ReportReqData(String deviceInfo, Configure configure) {
        super(deviceInfo, configure);
    }

    public String getInterface_url() {
        return interface_url;
    }

    public void setInterface_url(String interface_url) {
        this.interface_url = interface_url;
    }

    public int getExecute_time_cost() {
        return execute_time_cost;
    }

    public void setExecute_time_cost(int execute_time) {
        this.execute_time_cost = execute_time;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
