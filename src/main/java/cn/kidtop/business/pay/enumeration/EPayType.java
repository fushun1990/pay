package cn.kidtop.business.pay.enumeration;


import cn.kidtop.framework.base.BaseEnum;

/**
 * 支付方式
 * @author zhoup
 *
 */
public enum EPayType implements BaseEnum<Integer> {
	/**
	 * 支付类型1支付宝2微信
	 */
	ALIPAY(1,"支付宝"),
	WECHAT(2,"微信");
	
	
	private Integer code;

	private String text;

	EPayType(Integer code, String text) {
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
