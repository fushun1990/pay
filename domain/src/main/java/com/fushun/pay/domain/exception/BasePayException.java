package com.fushun.pay.domain.exception;

import com.fushun.framework.util.exception.exception.BusinessException;

/**
 * 支付通知异常
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月2日
 */
public class BasePayException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public BasePayException(BasePayExceptionEnum baseExceptionEnum) {
        super(baseExceptionEnum);
    }

    public BasePayException(String message, BasePayCustomizeMessageExceptionEnum baseCustomizeMessageExceptionEnum) {
        super(message, baseCustomizeMessageExceptionEnum);
    }

    public BasePayException(String message, Throwable cause, BasePayCustomizeMessageExceptionEnum baseCustomizeMessageExceptionEnum) {
        super(message, cause, baseCustomizeMessageExceptionEnum);
    }

    public BasePayException(Throwable cause, BasePayExceptionEnum baseExceptionEnum) {
        super(cause, baseExceptionEnum);
    }

    public interface BasePayExceptionEnum extends BusinessExceptionEnum {
    }

    ;

    public interface BasePayCustomizeMessageExceptionEnum extends BusinessCustomizeMessageExceptionEnum {
    }

    ;
}
