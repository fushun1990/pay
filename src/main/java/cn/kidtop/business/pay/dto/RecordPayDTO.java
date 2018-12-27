package cn.kidtop.business.pay.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the superisong_ep_recordPay database table.
 * 
 */
public class RecordPayDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date createdAt;

	private String orderPayNo;

	private String outTradeNo;

	private String payAccount;

	private Date payDate;

	private BigDecimal payMoney;

	private String payNo;

	private String payWay;

	private String receiveAccourt;

	private String receiveWay;

	private Integer status;

	private Date updatedAt;

	private String payFrom;
	
	/**
	 * 系统通知 状态，1：未通知，2：已通知
	 */
	private Integer notityStatus;
	
	public String getPayFrom() {
		return payFrom;
	}

	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}
	
	public RecordPayDTO() {
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getOrderPayNo() {
		return orderPayNo;
	}

	public void setOrderPayNo(String orderPayNo) {
		this.orderPayNo = orderPayNo;
	}

	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getPayAccount() {
		return this.payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public BigDecimal getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayNo() {
		return this.payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getPayWay() {
		return this.payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getReceiveAccourt() {
		return this.receiveAccourt;
	}

	public void setReceiveAccourt(String receiveAccourt) {
		this.receiveAccourt = receiveAccourt;
	}

	public String getReceiveWay() {
		return this.receiveWay;
	}

	public void setReceiveWay(String receiveWay) {
		this.receiveWay = receiveWay;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * 系统通知 状态，1：未通知，2：已通知
	 */
	public Integer getNotityStatus() {
		return notityStatus;
	}

	/**
	 * 系统通知 状态，1：未通知，2：已通知
	 */
	public void setNotityStatus(Integer notityStatus) {
		this.notityStatus = notityStatus;
	}


}