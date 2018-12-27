package cn.kidtop.business.pay.facade.pay.impl.winxinpay.dto;

import cn.kidtop.business.pay.dto.PayParamDTO;

/**
 * 微信支付，参数对象
 * @author fushun
 *
 * @version dev706
 * @creation 2017年6月1日
 */
public class WeChatPayParamDTO extends PayParamDTO{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 微信 公众号支付  授权code
	 */
	private String weiXinAuthCode;
	
	/**
	 * 微信openId
	 */
	private String openId;
	

	public String getWeiXinAuthCode() {
		return weiXinAuthCode;
	}

	public void setWeiXinAuthCode(String weiXinAuthCode) {
		this.weiXinAuthCode = weiXinAuthCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "WeChatPayParamDTO [weiXinAuthCode=" + weiXinAuthCode + ", openId=" + openId + "]";
	}

	
}
