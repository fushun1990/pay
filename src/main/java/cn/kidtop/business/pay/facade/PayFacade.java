package cn.kidtop.business.pay.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.kidtop.business.pay.dto.PayParamDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayAppPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayDirectPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayWapPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeChatAppPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeChatOfficialAccountsPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.dto.WeChatPayParamDTO;
import cn.kidtop.business.pay.validator.group.CreateAlipayAppPayResultValidatorGroup;
import cn.kidtop.business.pay.validator.group.CreateAlipayDirectPayResultValidatorGroup;
import cn.kidtop.business.pay.validator.group.CreateAlipayWapResultValidatorGroup;
import cn.kidtop.business.pay.validator.group.CreateWeiXinAppPayValidatorGroup;
/**
 * 支付接口
 * @author fushun
 *
 * @version dev706
 * @creation 2017年6月1日
 */
@Component
public class PayFacade {
	
	@Autowired
	private AlipayAppPayFacade alipayAppPayFacade;
	@Autowired
	private AlipayDirectPayFacade alipayDirectPayFacade;
	@Autowired
	private WeChatOfficialAccountsPayFacade weChatOfficialAccountsPayFacade;
	@Autowired
	private WeChatAppPayFacade weiXinAppPayFacade;

	@Autowired
	private AlipayWapPayFacade alipayWapPayFacade;

	/**
	 * 支付宝 app支付接口
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月18日
	 * @records <p>  fushun 2016年9月18日</p>
	 */
//	@Validation(validateGroup={CreateAlipayAppPayResultValidatorGroup.class},validateType={PayParamDTO.class})
	public String createAlipayAppPayResult(PayParamDTO payParamDTO){
		CallBack.checkCallBack(payParamDTO.getPayFrom());
		return alipayAppPayFacade.pay(payParamDTO);
	}
	
	/**
	 * 创建  支付宝  wap 支付表单
	 * @param payParamDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
//	@Validation(validateGroup={CreateAlipayWapResultValidatorGroup.class},validateType={PayParamDTO.class})
	public String createAlipayWapResult(PayParamDTO payParamDTO){
		CallBack.checkCallBack(payParamDTO.getPayFrom());
		return alipayWapPayFacade.pay(payParamDTO);
	}
	
	/**
	 * 创建web 即时到账  支付接口
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月18日
	 * @records <p>  fushun 2016年9月18日</p>
	 */
//	@Validation(validateGroup=CreateAlipayDirectPayResultValidatorGroup.class,validateType=PayParamDTO.class)
	public String createAlipayDirectPayResult(PayParamDTO payParamDTO){
		CallBack.checkCallBack(payParamDTO.getPayFrom());
		return alipayDirectPayFacade.pay(payParamDTO);
	}
	
	 
	 /**
	  * 微信公众号支付
	  * @param payParamDTO
	  * @return
	  * @author fushun
	  * @version V3.0商城
	  * @creation 2017年1月5日
	  * @records <p>  fushun 2017年1月5日</p>
	  */
//	 @Validation(validateGroup={CreateWeiXinJZHPayValidatorGroup.class},validateType={WeChatPayParamDTO.class})
	 public String createWeChatOfficialAccountsPay(WeChatPayParamDTO payParamDTO){
		 CallBack.checkCallBack(payParamDTO.getPayFrom());
		 payParamDTO.setPayWay(EPayWay.PAY_WAY_WEIXINPAY);
		 return weChatOfficialAccountsPayFacade.pay(payParamDTO);
	 }
	 
	 /**
	  * 微信App 支付
	  * @param payParamDTO
	  * @return
	  * @author fushun
	  * @version devlogin
	  * @creation 2017年5月26日
	  * @records <p>  fushun 2017年5月26日</p>
	  */
//	 @Validation(validateGroup={CreateWeiXinAppPayValidatorGroup.class},validateType={PayParamDTO.class})
	 public String createWeiXinAppPay(WeChatPayParamDTO payParamDTO){
		 CallBack.checkCallBack(payParamDTO.getPayFrom());
		 payParamDTO.setPayWay(EPayWay.PAY_WAY_APPC_WEIXINPAY);
		 return weiXinAppPayFacade.pay(payParamDTO);
	 }
	 
	 /**
	  * 根据微信授权code获取openId
	  * @param weiXinAuthCode
	  * @return
	  */
	 public String getWeChatOpenIdByCode(String weiXinAuthCode){
		 return weChatOfficialAccountsPayFacade.getWeChatOpenIdByCode(weiXinAuthCode);
	 }
	
}
