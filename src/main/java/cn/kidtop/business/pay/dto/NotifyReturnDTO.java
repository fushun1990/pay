package cn.kidtop.business.pay.dto;

/**
 * 支付异步通知，返回对象
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2017年1月5日
 */
public class NotifyReturnDTO {
	/**
	 * 成功返回字段
	 */
	private String success;
	/**
	 * 失败返回字段
	 */
	private String fail;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getFail() {
		return fail;
	}
	public void setFail(String fail) {
		this.fail = fail;
	}
	
	
}
