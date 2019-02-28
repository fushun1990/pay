package com.fushun.pay.domain.exception;

/**
 * 退款异常
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月5日
 */
public class RefundException extends BasePayException {

    /**
     * 已退款
     */
    public static final long REFUND_FLUSH = 130202;
    //13 means System Exception, 01 means web, 01 means request error
    private static final long serialVersionUID = 1L;


    public RefundException(Enum baseExceptionEnum) {
        super(baseExceptionEnum);
    }

    public RefundException(String message, RefundCustomizeMessageEnum baseCustomizeMessageExceptionEnum) {
        super(message, baseCustomizeMessageExceptionEnum);
    }

    public RefundException(String message, Throwable cause, RefundCustomizeMessageEnum baseCustomizeMessageExceptionEnum) {
        super(message, cause, baseCustomizeMessageExceptionEnum);
    }

    public RefundException(Throwable cause, Enum baseExceptionEnum) {
        super(cause, baseExceptionEnum);
    }

    public static enum Enum implements BasePayExceptionEnum {
        BASE(30002L, "退款异常"),
        OVER_REFUND(30003L, "已退款");
        private Long code;

        private String text;

        Enum(Long code, String text) {
            this.code = code;
            this.text = text;
        }

        public Long getCode() {
            return code;
        }

        public void setCode(Long code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }


    public static enum RefundCustomizeMessageEnum implements BasePayCustomizeMessageExceptionEnum {
        BASE(30001L, "自定义退款异常");
        private Long code;

        private String text;

        RefundCustomizeMessageEnum(Long code, String text) {
            this.code = code;
            this.text = text;
        }

        public Long getCode() {
            return code;
        }

        public void setCode(Long code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
