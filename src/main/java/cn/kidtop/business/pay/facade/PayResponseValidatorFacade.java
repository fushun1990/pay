package cn.kidtop.business.pay.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayAppPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayWapPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeChatAppPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeChatOfficialAccountsPayFacade;

/***
 * 支付 同步返回值验证 
 * @author fushun
 *
 * @version dev706
 * @creation 2017年6月5日
 */
@Component
public class PayResponseValidatorFacade {
	

	@Autowired
	private AlipayAppPayFacade alipayAppPayFacade;
	@Autowired
	private WeChatOfficialAccountsPayFacade weiXinJZHPayFacade;
	@Autowired
	private WeChatAppPayFacade weiXinAppPayFacade;

	@Autowired
	private AlipayWapPayFacade alipayWapPayFacade;
	
	/**
	 * 微信 公众号 支付 同步  支付结果验证
	 * @param requestParams {"result":"微信返回参数","orderPayNo":"订单支付单号"}
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月6日
	 * @records <p>  fushun 2017年1月6日</p>
	 */
	@Transactional()
	public void weiXinJZHPayResponseValidator(String requestParams) {
			weiXinJZHPayFacade.payResponseValidator(requestParams);
	}
	

	
	/**
	 * 微信app 支付   支付结果验证
	 * @param requestParams {"result":"微信返回参数","orderPayNo":"订单支付单号"}
	 * @author fushun
	 * @version dev706
	 * @creation 2017年6月1日
	 * @records <p>  fushun 2017年6月1日</p>
	 */
	@Transactional()
	public void weiXinAppPayResponseValidator(String requestParams) {
			weiXinAppPayFacade.payResponseValidator(requestParams);
	} 
	
	
	/***
	 * 支付宝 wap 支付方式同步返回验证
	 * @param requestParams
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	public void appAliPayWapPayResponseValidator(String requestParams) {
		alipayWapPayFacade.payResponseValidator(requestParams);
	}
	
	/**
	 * 支付宝 app支付 同步  支付结果验证
	 * 
	 * @param requestParams
	 * @return
	 */
	@Transactional()
	public void appAliPayResponseValidator(String requestParams) {
		alipayAppPayFacade.payResponseValidator(requestParams);
	}
}
