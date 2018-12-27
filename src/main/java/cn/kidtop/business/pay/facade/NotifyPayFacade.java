package cn.kidtop.business.pay.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayAppPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayDirectPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayDirectRefundFacade;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayWapPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeChatAppPayFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeChatOfficialAccountsPayFacade;


/**
 * 回调通知接口
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2016年9月10日
 */
@Component
public class NotifyPayFacade {
	
	@Autowired
	private AlipayDirectPayFacade alipayDirectPayFacade;
	@Autowired
	private AlipayDirectRefundFacade alipayDirectRefundFacade;
	@Autowired
	private AlipayAppPayFacade alipayAppPayFacade;
	@Autowired
	private AlipayWapPayFacade alipayWapPayFacade;
	@Autowired
	private WeChatOfficialAccountsPayFacade weiXinJZHPayFacade;
	@Autowired
	private WeChatAppPayFacade weiXinAppPayFacade;
	
	/**
	 * 支付宝 即时支付 通知 接口
	 * @param paramMap
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月10日
	 * @records <p>  fushun 2016年9月10日</p>
	 */
	public String alipayDirectPayNotify(Map<String, String> paramMap) {
		return alipayDirectPayFacade.payNotifyAlipay(paramMap);
	}
	
	/**
	 * 支付 即时支付 退款 通知回调接口
	 * @param paramMap
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月10日
	 * @records <p>  fushun 2016年9月10日</p>
	 */
	public String alipayDirectRefundNotify(Map<String, String> paramMap) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.putAll(paramMap);
		return alipayDirectRefundFacade.refundNotifyAlipay(map,EPayWay.PAY_WAY_ALIPAY);
	}

	/**
	 * 支付宝 app支付结果通知验证
	 * @param paramMap
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年11月22日
	 * @records <p>  fushun 2016年11月22日</p>
	 */
	public String alipayAppPayNotify(Map<String, String> paramMap) {
		return alipayAppPayFacade.payNotifyAlipay(paramMap);
	}
	
	/**
	 * 手机支付通知页面 通知验证接口
	 * @param paramMap
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	public String alipayWapNotify(Map<String, String> paramMap) {
		return alipayWapPayFacade.payNotifyAlipay(paramMap);
	}
	
	/**
	 * 微信支付 公众号支付 通知验证接口
	 * @param paramMap
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	public String weiXinJZHPayNotify(Map<String, String> paramMap) {
		return weiXinJZHPayFacade.payNotifyAlipay(paramMap);
	}
	
	/**
	 * 微信 app支付 通知验证接口
	 * @return
	 * @author fushun
	 * @version dev706
	 * @creation 2017年6月1日
	 * @records <p>  fushun 2017年6月1日</p>
	 */
	public String weiXinAppCPayNotify(Map<String, String> paramMap) {
		return weiXinAppPayFacade.payNotifyAlipay(paramMap);
	}

}
