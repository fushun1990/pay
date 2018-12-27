package cn.kidtop.business.pay.dto;

import cn.kidtop.framework.base.BaseDTO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the superisong_ep_recordRefund database table.
 * 
 */
public class RecordRefundDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal id;
	
	/**
	 * 第三方平台的接单单号
	 */
	private String payNo;
	
	/**
	 * 订单号
	 */
	private String outTradeNo;

	private String payWay;

	private String refundFrom;

	private BigDecimal refundMoney;

	private String refundNo;

	private String refundReason;

	private String result;

	private Integer status;

	/**
	 * 通知状态 1:已通知，2：未通知
	 */
	private Integer noticeStatus;
	
	public RecordRefundDTO() {
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
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

	public String getRefundFrom() {
		return this.refundFrom;
	}

	public void setRefundFrom(String refundFrom) {
		this.refundFrom = refundFrom;
	}

	public BigDecimal getRefundMoney() {
		return this.refundMoney;
	}

	public void setRefundMoney(BigDecimal refundMoney) {
		this.refundMoney = refundMoney;
	}

	public String getRefundNo() {
		return this.refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getRefundReason() {
		return this.refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(Integer noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	
	
}