package cn.kidtop.business.pay.cmp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.kidtop.framework.base.BaseCMP;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * The persistent class for the superisong_ep_recordPay database table.
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="kidtop_common_record_pay")
@IdClass(RecordPayId.class)
public class RecordPayCMP extends BaseCMP implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 支付订单号
	 */
	private String orderPayNo;

	/**
	 * 外部订单号
	 */
	@Id
	private String outTradeNo;

	/**
	 * 支付账号
	 */
	private String payAccount;

	/**
	 * 支付日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date payDate;

	/**
	 * 支付金额
	 */
	private BigDecimal payMoney;

	/**
	 * 支付单号
	 */
	private String payNo;

	/**
	 * 支付方式，1、支付宝，2银联
	 */
	private String payWay;

	/**
	 * 接收账户
	 */
	private String receiveAccourt;

	/**
	 * 接收方式
	 */
	private String receiveWay;

	/**
	 * 支付状态\r\n            [1 成功 2失败，3：创建]
	 */
	private Integer status;

	/**
	 * 支付端，1、web,2、app
	 */
	private String payFrom;
	
	/**
	 * 系统通知 状态，2：未通知，1：已通知
	 */
	private Integer notityStatus;
	
	public String getPayFrom() {
		return payFrom;
	}

	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}

	public RecordPayCMP() {
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

	/**
	 * 系统通知 状态，2：未通知，1：已通知
	 */
	public Integer getNotityStatus() {
		return notityStatus;
	}

	/**
	 * 系统通知 状态，2：未通知，1：已通知
	 */
	public void setNotityStatus(Integer notityStatus) {
		this.notityStatus = notityStatus;
	}

	@Override
	public String toString() {
		return "RecordPayCMP [orderPayNo=" + orderPayNo + ", outTradeNo=" + outTradeNo + ", payAccount=" + payAccount
				+ ", payDate=" + payDate + ", payMoney=" + payMoney + ", payNo=" + payNo + ", payWay=" + payWay
				+ ", receiveAccourt=" + receiveAccourt + ", receiveWay=" + receiveWay + ", status=" + status
				+ ", payFrom=" + payFrom + ", notityStatus=" + notityStatus + "]";
	}

}