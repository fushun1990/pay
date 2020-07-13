package com.fushun.pay.thirdparty.weixin.pay.exception;


import com.fushun.framework.exception.BusinessException;

/**
 * 支付通知异常
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月2日
 */
public class BasePayException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public BasePayException(IBasePayExceptionEnum businessExceptionEnum) {
        super(businessExceptionEnum);
    }

    public BasePayException(IBasePayExceptionEnum businessExceptionEnum, String logMessage) {
        super(businessExceptionEnum, logMessage);
    }

    public BasePayException(Throwable cause, IBasePayExceptionEnum businessExceptionEnum) {
        super(cause, businessExceptionEnum);
    }

    public BasePayException(Throwable cause, IBasePayExceptionEnum businessExceptionEnum, String logMessage) {
        super(cause, businessExceptionEnum, logMessage);
    }


    @Override
    protected String getExceptionCode() {
        return "";
    }

    public interface IBasePayExceptionEnum extends IBusinessExceptionEnum {
    }
}
