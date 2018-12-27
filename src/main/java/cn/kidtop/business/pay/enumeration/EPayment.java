package cn.kidtop.business.pay.enumeration;


import cn.kidtop.framework.base.BaseEnum;

/**
 * 支付方式
 * @author zhoup
 *
 */
public enum EPayment implements BaseEnum<Integer> {
	/**
	 * 支付方式 1、在线
	 */
	ONLINE(1,"在线");
	
	
	private Integer code;

	private String text;

	EPayment(Integer code, String text) {
        this.code = code;
        this.text = text;
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
