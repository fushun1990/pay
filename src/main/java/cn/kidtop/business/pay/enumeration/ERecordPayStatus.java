package cn.kidtop.business.pay.enumeration;


import cn.kidtop.business.pay.exception.PayException;
import cn.kidtop.framework.base.BaseEnum;

/**
 * 支付信息状态
 * @author zhoup
 *
 */
public enum ERecordPayStatus implements BaseEnum<Integer> {
	success(1,"成功"),
	failed(2,"失败"),
	created(3,"创建");
	
	private Integer code;

	private String text;

	ERecordPayStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
	
	/**
	 * 检测支付状态 是否可以更新
	 * @param nextERecordPayStatus
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月17日
	 * @records <p>  fushun 2016年9月17日</p>
	 */
	public void checkStatus(ERecordPayStatus nextERecordPayStatus){
		switch (this) {
			case created:
				switch (nextERecordPayStatus) {
					case failed:
						return ;
					case success:
						return ;
					default:
						break;
				}
				break;
			case failed:
				switch (nextERecordPayStatus) {
					case success:
						return ;
					case failed:
						return;
					default:
						break;
				}		
				break;
			case success:
				switch (nextERecordPayStatus) {
					case success:
						return ;
					default:
						break;
				}	
			default:
				break;
		}
		throw new PayException(PayException.Enum.PAY_STATUS_ERROR_EXCEPTION);
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
