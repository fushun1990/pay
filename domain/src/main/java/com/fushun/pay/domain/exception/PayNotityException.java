package com.fushun.pay.domain.exception;

/**
 * 支付通知异常
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月2日
 */
public class PayNotityException extends BasePayException {


    public PayNotityException(Enum baseExceptionEnum) {
        super(baseExceptionEnum);
    }

    public PayNotityException(String message, PayNotityCustomizeMessageEnum baseCustomizeMessageExceptionEnum) {
        super(message, baseCustomizeMessageExceptionEnum);
    }

    public PayNotityException(String message, Throwable cause, PayNotityCustomizeMessageEnum baseCustomizeMessageExceptionEnum) {
        super(message, cause, baseCustomizeMessageExceptionEnum);
    }

    public PayNotityException(Throwable cause, Enum baseExceptionEnum) {
        super(cause, baseExceptionEnum);
    }

    public static enum Enum implements BasePayExceptionEnum {
        BASE(30002L, "支付通知异常"),
        PAYENT_ERROR(30004L, "支付方式错误"),;
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

    public static enum PayNotityCustomizeMessageEnum implements BasePayCustomizeMessageExceptionEnum {
        BASE(30001L, "自定义支付通知异常");
        private Long code;

        private String text;

        PayNotityCustomizeMessageEnum(Long code, String text) {
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
