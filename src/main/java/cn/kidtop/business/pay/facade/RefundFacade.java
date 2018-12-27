package cn.kidtop.business.pay.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.kidtop.business.pay.dto.RefundParamDTO;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayAppRefundFacade;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayDirectRefundFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeiXinRefundFacade;
import cn.kidtop.business.pay.validator.group.CreateDirectRefundResultValidatorGroup;
import cn.kidtop.business.pay.validator.group.RefundAppAlipayRefundValidatorGroup;
import cn.kidtop.business.pay.validator.group.WeiXinRefundValidatorGroup;

/**
 * 退款操作
 * @author fushun
 *
 * @version dev706
 * @creation 2017年6月1日
 */
@Component
public class RefundFacade {

	@Autowired
	private AlipayDirectRefundFacade alipayDirectRefundFacade;
	@Autowired
	private WeiXinRefundFacade weiXinRefundFacade;
	@Autowired
	private AlipayAppRefundFacade alipayAppRefundFacade;
	
	/**
	 * 微信退款 接口
	 * @param refundParamDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月6日
	 * @records <p>  fushun 2017年1月6日</p>
	 */
//	@Validation(validateType=RefundParamDTO.class,validateGroup=WeiXinRefundValidatorGroup.class)
	public void weiXinRefund(RefundParamDTO refundParamDTO){
		weiXinRefundFacade.refund(refundParamDTO);
	}
	
	/**
	 * 支付宝 app 支付退款
	 * @param refundParamDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月18日
	 * @records <p>  fushun 2016年9月18日</p>
	 */
//	@Validation(validateType=RefundParamDTO.class,validateGroup={RefundAppAlipayRefundValidatorGroup.class})
	public void refundAppAlipayRefund(RefundParamDTO refundParamDTO){
		 alipayAppRefundFacade.refund(refundParamDTO);
	}
	
	/**
	 * 创建  支付宝 即时到账 退款 接口
	 * @param refundParamDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月3日
	 * @records <p>  fushun 2017年1月3日</p>
	 */
//	@Validation(validateType=RefundParamDTO.class,validateGroup=CreateDirectRefundResultValidatorGroup.class)
	 public String createDirectRefundResult(RefundParamDTO refundParamDTO){
		 return alipayDirectRefundFacade.refundResult(refundParamDTO);
	 }
}
