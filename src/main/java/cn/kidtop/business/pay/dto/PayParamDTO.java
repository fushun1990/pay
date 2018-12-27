package cn.kidtop.business.pay.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import cn.kidtop.business.pay.enumeration.EPayFrom;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.validator.group.CreateAlipayAppPayResultValidatorGroup;
import cn.kidtop.business.pay.validator.group.CreateAlipayDirectPayResultValidatorGroup;
import cn.kidtop.business.pay.validator.group.CreateAlipayWapResultValidatorGroup;
import cn.kidtop.business.pay.validator.group.CreateWeiXinAppPayValidatorGroup;
import cn.kidtop.business.pay.validator.group.CreateWeiXinJZHPayValidatorGroup;

public class PayParamDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 支付方式
	 */
	@NotNull(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
	private EPayWay payWay; //支付宝，网银，银联等
	/**
	 * 支付源
	 */
	@NotNull(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
	private EPayFrom payFrom; //web端，App端
	/**
	 * 通知连接
	 */
	@NotEmpty(groups={CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
	private String notifyUrl;
	/**
	 * 返回连接
	 */
	@NotEmpty(groups={CreateAlipayWapResultValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
	private String returnUrl;
	/**
	 * 支付单号
	 */
	@NotEmpty(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
	private String tradeNo;
	
	/**
	 * 支付 标题
	 */
	@NotEmpty(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
	private String subject;
	
	/**
	 * 支付信息描述
	 */
	@NotEmpty(groups={CreateWeiXinJZHPayValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class})
	private String body;
	
	/**
	 * 用户终端ip
	 */
	@NotEmpty(groups={CreateWeiXinJZHPayValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class})
	private String spbillCreateIp;
	
	
	/**
	 * 支付金额
	 * 保留两位小数
	 */
	@NotNull(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
	private BigDecimal totalFee;
	
	
	
	public EPayWay getPayWay() {
		return payWay;
	}



	public void setPayWay(EPayWay payWay) {
		this.payWay = payWay;
	}



	public EPayFrom getPayFrom() {
		return payFrom;
	}



	public void setPayFrom(EPayFrom payFrom) {
		this.payFrom = payFrom;
	}



	public String getNotifyUrl() {
		return notifyUrl;
	}



	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}



	public String getReturnUrl() {
		return returnUrl;
	}



	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}



	public String getTradeNo() {
		return tradeNo;
	}



	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public BigDecimal getTotalFee() {
		return totalFee;
	}



	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}



	public String getBody() {
		return body;
	}



	public void setBody(String body) {
		this.body = body;
	}



	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}



	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}



	@Override
	public String toString() {
		return "PayParamDTO [payWay=" + payWay + ", payFrom=" + payFrom
				+ ", notifyUrl=" + notifyUrl + ", returnUrl=" + returnUrl
				+ ", tradeNo=" + tradeNo + ", subject=" + subject
				+ ", totalFee=" + totalFee + "]";
	}
	
}