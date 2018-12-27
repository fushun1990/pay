package cn.kidtop.business.pay.facade.pay.impl.winxinpay.exception;

import cn.kidtop.framework.exception.BusinessException;

/**
 * 微信退款异常（资金不足），做特殊处理
 * Created by Administrator on 2017/7/4.
 */
public class WeiXinRefundException extends BusinessException{

	private static final long serialVersionUID = 1L;


    public WeiXinRefundException(BusinessExceptionEnum baseExceptionEnum) {
        super(baseExceptionEnum);
    }

    public WeiXinRefundException(String message, BusinessCustomizeMessageExceptionEnum baseCustomizeMessageExceptionEnum) {
        super(message, baseCustomizeMessageExceptionEnum);
    }

    public WeiXinRefundException(String message, Throwable cause, BusinessCustomizeMessageExceptionEnum baseCustomizeMessageExceptionEnum) {
        super(message, cause, baseCustomizeMessageExceptionEnum);
    }

    public WeiXinRefundException(Throwable cause, BusinessExceptionEnum baseExceptionEnum) {
        super(cause, baseExceptionEnum);
    }
}
