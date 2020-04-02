package com.fushun.pay.thirdparty.weixin.pay.exception;


import com.fushun.framework.exception.BusinessException;
import com.fushun.pay.domain.exception.BasePayException;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.domain.exception.RefundException;

/**
 * 微信退款异常（资金不足），做特殊处理
 * Created by Administrator on 2017/7/4.
 */
public class WeiXinRefundException extends RefundException {

    private static final long serialVersionUID = 1L;

    public WeiXinRefundException(IRefundExceptionEnum refundExceptionEnum) {
        super(refundExceptionEnum);
    }

    public WeiXinRefundException(IRefundExceptionEnum refundExceptionEnum, String logMessage) {
        super(refundExceptionEnum, logMessage);
    }

    public WeiXinRefundException(Throwable cause, IRefundExceptionEnum refundExceptionEnum) {
        super(cause, refundExceptionEnum);
    }

    public WeiXinRefundException(Throwable cause, IRefundExceptionEnum refundExceptionEnum, String logMessage) {
        super(cause, refundExceptionEnum, logMessage);
    }
}
