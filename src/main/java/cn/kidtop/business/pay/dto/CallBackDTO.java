package cn.kidtop.business.pay.dto;

import cn.kidtop.business.pay.enumeration.EPayWay;

/**
 * 应用内部通知对象
 * @date: 2017-09-20 16:12:34
 * @author:wangfushun
 * @version 1.0
 */
public class CallBackDTO {
	
	/**
	 * 如果是支付通知，则是支付单号
	 * 如果是退款通知，则是退款单号
	 */
	private String callBackNo;
	
	/**
	 * 支付方式，退款方式
	 */
	private EPayWay ePayWay;
	
	/**
	 * 信息
	 */
	private String message;

	
	public CallBackDTO(String callBackNo, EPayWay ePayWay) {
		this.callBackNo = callBackNo;
		this.ePayWay = ePayWay;
	}

	public String getCallBackNo() {
		return callBackNo;
	}

	public void setCallBackNo(String callBackNo) {
		this.callBackNo = callBackNo;
	}

	public EPayWay getePayWay() {
		return ePayWay;
	}

	public void setePayWay(EPayWay ePayWay) {
		this.ePayWay = ePayWay;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CallBackDTO [callBackNo=" + callBackNo + ", ePayWay=" + ePayWay + ", message=" + message + "]";
	}

	
}
