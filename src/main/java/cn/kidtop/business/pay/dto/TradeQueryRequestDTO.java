package cn.kidtop.business.pay.dto;

import cn.kidtop.business.pay.enumeration.EPayFrom;
import cn.kidtop.framework.base.BaseDTO;

public class TradeQueryRequestDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private String outTradeNo;
	
	private EPayFrom ePayFrom;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public EPayFrom getePayFrom() {
		return ePayFrom;
	}

	public void setePayFrom(EPayFrom ePayFrom) {
		this.ePayFrom = ePayFrom;
	}
	
	
	
}
