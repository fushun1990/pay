package cn.kidtop.business.pay.enumeration;


import cn.kidtop.business.pay.exception.PayException;
import cn.kidtop.framework.base.BaseEnum;

/**
 * 退款支付状态
 * @author zhoup
 *
 */
public enum ERefundStatus implements BaseEnum<Integer> {
	wait(1,"等待退款 "),
	success(2,"退款成功"),
	fail(3,"退款失败");
	
	private Integer code;

	private String text;

	ERefundStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
	
	/**
	 * 检测状态信息
	 * @param nextERecordRefundStatus 下一步状态
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月17日
	 * @records <p>  fushun 2016年9月17日</p>
	 */
	public void checkStatus(ERefundStatus nextERecordRefundStatus){
		switch (this) {
			case wait:
				switch (nextERecordRefundStatus) {
					case success:
						return ;
					case fail:
						return;
					default:
						break;
				}
				break;
			case success:
				switch (nextERecordRefundStatus) {
					case success:
						return ;
					default:
						break;
				}
			case fail:
				switch (nextERecordRefundStatus) {
					case success:
						return ;
					case fail:
						return;
					default:
						break;
				}
				break;
			default:
				break;
		}
		throw new PayException(PayException.Enum.PAY_REFUND_STATUS_ERROR_EXCEPTION);
	}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

}
