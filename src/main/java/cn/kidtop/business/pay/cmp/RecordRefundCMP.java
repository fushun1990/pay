package cn.kidtop.business.pay.cmp;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.kidtop.framework.base.BaseCMP;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;



/**
 * 支付订单退款记录表
 * The persistent class for the superisong_ep_recordRefund database table.
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="kidtop_common_record_refund")
public class RecordRefundCMP extends BaseCMP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private BigDecimal id;
	
	private String payNo;

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

	public RecordRefundCMP() {
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

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
}