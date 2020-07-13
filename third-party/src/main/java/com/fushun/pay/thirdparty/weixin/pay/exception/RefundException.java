package com.fushun.pay.thirdparty.weixin.pay.exception;

/**
 * 退款异常
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月5日
 */
public class RefundException extends BasePayException {


    public RefundException(IRefundExceptionEnum refundExceptionEnum) {
        super(refundExceptionEnum);
    }

    public RefundException(IRefundExceptionEnum refundExceptionEnum, String logMessage) {
        super(refundExceptionEnum, logMessage);
    }

    public RefundException(Throwable cause, IRefundExceptionEnum refundExceptionEnum) {
        super(cause, refundExceptionEnum);
    }

    public RefundException(Throwable cause, IRefundExceptionEnum refundExceptionEnum, String logMessage) {
        super(cause, refundExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "REFUND_";
    }

    public static enum RefundExceptionEnum implements IRefundExceptionEnum {
        /**
         * 支付错误
         */
        REFUND_ERROR( "退款异常"),
        OVER_REFUND( "已退款");

        private String msg;

        private RefundExceptionEnum(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return this.msg;
        }
    }

    public interface IRefundExceptionEnum extends IBasePayExceptionEnum {
    }


}
