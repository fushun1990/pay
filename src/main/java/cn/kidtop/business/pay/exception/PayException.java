package cn.kidtop.business.pay.exception;


import cn.kidtop.framework.exception.BusinessException;

/**
 * 支付通知异常
 * @author fushun
 *
 * @version dev706
 * @creation 2017年6月2日
 */
public class PayException extends BasePayException {

	private static final long serialVersionUID = 1L;

	public PayException(BasePayExceptionEnum baseExceptionEnum) {
		super(baseExceptionEnum);
	}

	public PayException(String message, PayCustomizeMessageEnum payCustomizeMessageEnum) {
		super(message, payCustomizeMessageEnum);
	}

	public PayException(String message, Throwable cause, PayCustomizeMessageEnum payCustomizeMessageEnum) {
		super(message, cause, payCustomizeMessageEnum);
	}

	public PayException(Throwable cause, BasePayExceptionEnum baseExceptionEnum) {
		super(cause, baseExceptionEnum);
	}


	public static enum Enum implements BasePayExceptionEnum {

		PAY_STATUS_ERROR_EXCEPTION(30002L,"支付状态错误"), 
		PAY_REFUND_STATUS_ERROR_EXCEPTION(30003L,"支付退款状态错误"), 
		PAY_INFO_NO_EXISTS_EXCEPTION(30004L,"支付信息不存在"), 
		PAY_MONEY_MISMATCHING_EXCEPTION(30005L,"支付金额不匹配"), 
		REFUND_INFO_NO_EXISTS_EXCEPTION(30006L,"退款信息不存在"), 
		REFUND_STATUS_ERROR_EXCEPTION(30007L,"退款状态错误"), 
		PAY_CREATE_FAILED_EXCEPTION(8L,"创建支付失败"), 
		SIGNATURE_VALIDATION_FAILED_EXCEPTION(30009L,"签名验证失败"), 
		ALIPAY_ORDER_STATUS_EXCEPTION(300010L,"支付宝订单状态异常"), 
		PAY_BUSINESS_EXCEPTION(300011L,"支付业务异常"), 
		PAY_RETURN_STATUS_ERROR_EXCEPTION(300012L,"支付返回状态错误"), 
		PAY_CODE_ERROR_EXCEPTION(300013L,"支付code错误"), 
		REFUND_REQUEST_FAILED_EXCEPTION(300014L,"退款请求失败"), 
		QUERY_REQUEST_FAILED_EXCEPTION(300015L,"查询请求失败"), 
		VALIDATION_RETURN_UNDEFINED_EXCEPTION(300016L,"验证返回未知异常"), 
		PAY_FAILED_EXCEPTION(300017L,"支付失败"), 
		WECHAT_CODE_NOT_NULL_EXCEPTION(300018L,"微信code不能为空"), 
		REQUEST_AUTHORIZATION_FAILED_EXCEPTION(300019L,"获取授权失败"),
		REFUND_ERROR_EXCEPTION(300020L,"退款错误"), 
		PAY_SUCCESS_EXCEPTION(300021L,"已支付成功"), 
		CALLBACK_URL_ERROR_EXCEPTION(300022L,"回调地址错误"), 
		PAY_ORDERNO_EXISTS_EXCEPTION(300023L,"支付单号不存在"), 
		REFUNDING_EXCEPTION(300024L,"正在退款中"), 
		REFUNDED_EXCEPTION(300025L,"已退款"), 
		CALLBACK_REGISTERED_EXCEPTION(300026L,"回调方法已注册"), 
		CALLBACK_NO_EXISTS_EXCEPTION(300027L,"回调方法不存在,请注册回调方法"),
		PAY_NOTITY_EXCEPTION(300027L,"已收款，更新失败。请联系客服！"),
		BASECODE_EXCEPTION(300028L,"支付错误"),
		;
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

	public static enum PayCustomizeMessageEnum implements BasePayCustomizeMessageExceptionEnum {
		CUSTOMIZE_MESSAGE_EXCEPTION(300001L,"自定义错误信息内容"),
		;
		private Long code;

		private String text;

		PayCustomizeMessageEnum(Long code, String text) {
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
