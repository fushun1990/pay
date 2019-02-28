package com.fushun.pay.infrastructure.pay.tunnel.database.dataobject;

import java.io.Serializable;

/**
 * 支付记录id主键
 * @date: 2017-09-20 11:36:35
 * @author:wangfushun
 * @version 1.0
 */
public class RecordPayId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String outTradeNo;
	
	public RecordPayId() {
	}
	
	public RecordPayId(String outTradeNo) {
		super();
		this.outTradeNo = outTradeNo;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	@Override
	public int hashCode() {
		return this.outTradeNo.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RecordPayId) {
			RecordPayId obj2=(RecordPayId) obj;
			return this.outTradeNo.equals(obj2.outTradeNo);
		}
		return false;
	}

	@Override
	public String toString() {
		return "RecordPayId [outTradeNo=" + outTradeNo + "]";
	}
	
}
