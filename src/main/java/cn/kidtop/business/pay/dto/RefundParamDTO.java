package cn.kidtop.business.pay.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//import org.hibernate.validator.constraints.NotEmpty;

import cn.kidtop.business.pay.enumeration.ERefundFrom;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.enumeration.ERefundAccount;
import cn.kidtop.business.pay.validator.group.CreateDirectRefundResultValidatorGroup;
import cn.kidtop.business.pay.validator.group.RefundAppAlipayRefundValidatorGroup;
import cn.kidtop.business.pay.validator.group.WeiXinRefundValidatorGroup;

public class RefundParamDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 退款源
	 */
	@NotNull(groups={WeiXinRefundValidatorGroup.class,RefundAppAlipayRefundValidatorGroup.class,CreateDirectRefundResultValidatorGroup.class})
	private ERefundFrom refundFrom;
	/**
	 * 通知地址
	 */
	@NotEmpty(groups={CreateDirectRefundResultValidatorGroup.class})
	private String notifyUrl;

	/**
	 * 退款单号
	 */
	@NotEmpty(groups={WeiXinRefundValidatorGroup.class,RefundAppAlipayRefundValidatorGroup.class,CreateDirectRefundResultValidatorGroup.class})
	private String refundNo;
	/**
	 * 退款金额
	 */
	@NotNull(groups={WeiXinRefundValidatorGroup.class,RefundAppAlipayRefundValidatorGroup.class,CreateDirectRefundResultValidatorGroup.class})
	private BigDecimal refundMoney;
	/**
	 * 支付单号
	 */
	@NotEmpty(groups={WeiXinRefundValidatorGroup.class,RefundAppAlipayRefundValidatorGroup.class,CreateDirectRefundResultValidatorGroup.class})
	private String outTradeNo;

	/**
	 * 退款原因
	 */
	@NotEmpty(groups={WeiXinRefundValidatorGroup.class,RefundAppAlipayRefundValidatorGroup.class,CreateDirectRefundResultValidatorGroup.class})
	private String refundReason;

	/**
	 * 微信退款源
	 * 退款源
	 */
	private ERefundAccount eRefundAccount;

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public ERefundFrom getRefundFrom() {
		return refundFrom;
	}

	public BigDecimal getRefundMoney() {
		return refundMoney;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setRefundFrom(ERefundFrom refundFrom) {
		this.refundFrom = refundFrom;
	}

	public void setRefundMoney(BigDecimal refundMoney) {
		this.refundMoney = refundMoney;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public ERefundAccount geteRefundAccount() {
		return eRefundAccount;
	}

	public void seteRefundAccount(ERefundAccount eRefundAccount) {
		this.eRefundAccount = eRefundAccount;
	}

	@Override
	public String toString() {
		return "RefundParamDTO [refundFrom=" + refundFrom + ", notifyUrl=" + notifyUrl + ", refundNo=" + refundNo
				+ ", refundMoney=" + refundMoney + ", outTradeNo=" + outTradeNo + ", refundReason=" + refundReason
				+ ", eRefundAccount=" + eRefundAccount + "]";
	}
}