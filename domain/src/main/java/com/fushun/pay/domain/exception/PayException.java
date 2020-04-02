package com.fushun.pay.domain.exception;


/**
 * 支付通知异常
 *
 * @author fushun
 * @version dev706
 * @creation 2017年6月2日
 */
public class PayException extends BasePayException {

    private static final long serialVersionUID = 1L;

    public PayException(IPayExceptionEnum payExceptionEnum) {
        super(payExceptionEnum);
    }

    public PayException(IPayExceptionEnum payExceptionEnum, String logMessage) {
        super(payExceptionEnum, logMessage);
    }

    public PayException(Throwable cause, IPayExceptionEnum payExceptionEnum) {
        super(cause, payExceptionEnum);
    }

    public PayException(Throwable cause, IPayExceptionEnum payExceptionEnum, String logMessage) {
        super(cause, payExceptionEnum, logMessage);
    }

    @Override
    protected String getExceptionCode() {
        return "PAY_";
    }

    public static enum PayExceptionEnum implements IPayExceptionEnum {
        /**
         * 支付错误
         */
        PAY_STATUS_ERROR("支付状态错误"),
        PAY_REFUND_STATUS_ERROR( "支付退款状态错误"),
        PAY_INFO_NO_EXISTS("支付信息不存在"),
        PAY_MONEY_MISMATCHING("支付金额不匹配"),
        REFUND_INFO_NO_EXISTS("退款信息不存在"),
        REFUND_STATUS_ERROR( "退款状态错误"),
        PAY_CREATE_FAILED( "创建支付失败"),
        SIGNATURE_VALIDATION_FAILED("签名验证失败"),
        ALIPAY_ORDER_STATUS( "支付宝订单状态异常"),
        PAY_BUSINESS("支付业务异常"),
        PAY_RETURN_STATUS_ERROR("支付返回状态错误"),
        PAY_CODE_ERROR( "支付code错误"),
        REFUND_REQUEST_FAILED("退款请求失败"),
        QUERY_REQUEST_FAILED( "查询请求失败"),
        VALIDATION_RETURN_UNDEFINED( "验证返回未知异常"),
        PAY_FAILED( "支付失败"),
        WECHAT_CODE_NOT_NULL( "微信code不能为空"),
        REQUEST_AUTHORIZATION_FAILED( "获取授权失败"),
        REFUND_ERROR( "退款错误"),
        PAY_SUCCESS( "已支付成功"),
        CALLBACK_URL_ERROR("回调地址错误"),
        PAY_ORDERNO_EXISTS("支付单号不存在"),
        REFUNDING( "正在退款中"),
        REFUNDED( "已退款"),
        CALLBACK_REGISTERED("回调方法已注册"),
        CALLBACK_NO_EXISTS("回调方法不存在,请注册回调方法"),
        PAY_NOTITY( "已收款，更新失败。请联系客服！"),
        BASECODE( "支付错误"),;

        private String msg;

        private PayExceptionEnum(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return this.msg;
        }
    }

    public interface IPayExceptionEnum extends IBasePayExceptionEnum {
    }

}
