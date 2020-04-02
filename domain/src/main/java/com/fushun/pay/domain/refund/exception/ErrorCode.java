package com.fushun.pay.domain.refund.exception;


import com.alibaba.cola.dto.ErrorCodeI;

/**
 * ErrorCode
 *
 * @author Frank Zhang
 * @date 2019-01-04 11:00 AM
 */
public enum ErrorCode implements ErrorCodeI {

    PAY_IS_NOT_EXIST("pay_is_not_exist", "支付信息不存在，不能退款"),
    PAY_IS_NOT_SUCCESS("pay_is_not_success", "支付未成功，不能退款"),
    REFUND_IS_NOT_EXIST("refund_is_not_exist", "退款失败"),
    REFUND_IS_SUCCESS("refund_is_success", "已退款成功"),
    REFUNDING("refunding", "正在退款中"),
    PAY_MONEY_IS_ZERO("refunding", "支付金额0元，无法退款"),
    PAY_MONEY_LESS_THAN_REFUND_MONEY("refunding", "支付金额小于退款金额");

    private final String errCode;
    private final String errDesc;

    private ErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    @Override
    public String getErrCode() {
        return errCode;
    }

    @Override
    public String getErrDesc() {
        return errDesc;
    }
}