package com.fushun.pay.domain.exception;

/**
 * 支付通知异常
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月2日
 */
public class PayNotityException extends BasePayException {


    public PayNotityException(IPayNotityExceptionEnum payNotityExceptionEnum) {
        super(payNotityExceptionEnum);
    }

    public PayNotityException(IPayNotityExceptionEnum payNotityExceptionEnum, String logMessage) {
        super(payNotityExceptionEnum, logMessage);
    }

    public PayNotityException(Throwable cause, IPayNotityExceptionEnum payNotityExceptionEnum) {
        super(cause, payNotityExceptionEnum);
    }

    public PayNotityException(Throwable cause, IPayNotityExceptionEnum payNotityExceptionEnum, String logMessage) {
        super(cause, payNotityExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "PAY_NOTITY_";
    }

    public static enum PayNotityExceptionEnum implements IPayNotityExceptionEnum {
        /**
         * 支付通知异常
         */
        PAY_ENTITY_ERROR( "支付通知异常");

        private String msg;

        private PayNotityExceptionEnum(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return this.msg;
        }
    }

    public interface IPayNotityExceptionEnum extends IBasePayExceptionEnum {
    }


}
