package com.fushun.pay.app.common.exception;

import com.alibaba.cola.dto.ErrorCodeI;

/**
 * ErrorCode
 *
 * @author Frank Zhang
 * @date 2019-01-04 11:00 AM
 */
public enum ErrorCode implements ErrorCodeI {

    CREATED_PAY_BODY("CREATED_PAY_BODY", "创建支付失败"),
    PAY_FAIL("PAY_FAIL", "支付失败"),
    PAY_SUCCESS("PAY_SUCCESS", "支付成功"),
    OAUTH20_FAIL("OAUTH20_FAIL", "授权失败"),
    REFUND_FAIL("REFUND_FAIL","退款失败"),
    REFUND_SUCCESS("REFUND_SUCCESS","退款成功");

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