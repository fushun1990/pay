package cn.kidtop.business.pay.facade.sdk.alipay.enumeration;


import cn.kidtop.framework.base.BaseEnum;

/**
 * 支付宝支 交易状态
 * @author fushun
 *
 * @version dev706
 * @creation 2017年6月2日
 */
public enum ETradeStatus implements BaseEnum<String> {
	/**
	 * 交易创建，等待买家付款
	 */
	WAIT_BUYER_PAY("WAIT_BUYER_PAY","（交易创建，等待买家付款）"),
	/**
	 * （未付款交易超时关闭，或支付完成后全额退款）
	 */
	TRADE_CLOSED("TRADE_CLOSED","（未付款交易超时关闭，或支付完成后全额退款）"),
	/**
	 * （交易支付成功）
	 */
	TRADE_SUCCESS("TRADE_SUCCESS","（交易支付成功）"),
	/**
	 * 交易结束，不可退款
	 */
	TRADE_FINISHED("TRADE_FINISHED","（交易结束，不可退款）");
	
	private String code;
	private String text;

	
	private ETradeStatus(String code,String text) {
		this.code=code;
		this.text=text;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
}
