package test.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.kidtop.business.pay.PayApplication;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import cn.kidtop.business.pay.dto.CallBackDTO;
import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.dto.RecordRefundDTO;
import cn.kidtop.business.pay.dto.RefundParamDTO;
import cn.kidtop.business.pay.enumeration.EPayFrom;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.facade.CallBack;
import cn.kidtop.business.pay.facade.JobPayFacade;
import cn.kidtop.business.pay.facade.NotifyPayFacade;
import cn.kidtop.business.pay.facade.PayFacade;
import cn.kidtop.business.pay.facade.PayResponseValidatorFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeiXinRefundFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.dto.WeChatPayParamDTO;
import cn.kidtop.framework.util.JsonUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayApplication.class)
public class WeiXinPayTest{

	@Autowired
	private PayFacade  payFacade;
	@Autowired
	private JobPayFacade jobPayFacade;
	@Autowired
	private WeiXinRefundFacade weiXinRefundFacade;
	@Autowired
	private PayResponseValidatorFacade payResponseValidatorFacade;
	@Autowired
	private NotifyPayFacade notifyPayFacade;
	
	
	@BeforeClass
	public static void postConstuct() {
		CallBack.registerCallBack(EPayFrom.PAY_FROM_APP_DOWN_VIDEO, WeiXinPayTest::updatePayStatusSuccess,
				WeiXinPayTest::updatePayStatusFailed);
	}
	
	@Test
	public void refundRequest(){
		RefundParamDTO refundParamDTO=new RefundParamDTO();
		refundParamDTO.setOutTradeNo("136199720220170116211332");
		refundParamDTO.setRefundMoney(BigDecimal.valueOf(0.01));
		RecordRefundDTO recordRefundDTO=new RecordRefundDTO();
		weiXinRefundFacade.refundRequest(refundParamDTO, recordRefundDTO);
	}
	
	@Test
	public void createWeiXinJSPay(){
		WeChatPayParamDTO payParamDTO = new WeChatPayParamDTO();
		payParamDTO.setPayWay(EPayWay.PAY_WAY_WEIXINPAY);
		payParamDTO.setPayFrom(EPayFrom.PAY_FROM_APP_DOWN_VIDEO);
		payParamDTO.setNotifyUrl("http://seller.superisong.com/home/pay/wxpay");
		payParamDTO.setReturnUrl("");
		payParamDTO.setTradeNo("201510160000000555549791145");
		payParamDTO.setTotalFee(BigDecimal.valueOf(0.01));
		payParamDTO.setBody("商城分享-单品支付");
		payParamDTO.setSpbillCreateIp("125.82.191.31");
		payParamDTO.setWeiXinAuthCode("071OS0p92Xfj1O0ykfp92Ed9p92OS0p2");
		String str = payFacade.createWeChatOfficialAccountsPay(payParamDTO);
		System.out.println(str);
	}
	
	
	/**
	 * 支付完成 确认
	 */
	public static void updatePayStatusSuccess(CallBackDTO payOrderNo) {
	}

	/**
	 * 更新失败状态
	 * 
	 * @param refundPayNo
	 */
	public static void updatePayStatusFailed(CallBackDTO refundPayNo) {
		// TODO 待实现
	}
	@Test
	public void createWeiXinAppPay(){
		WeChatPayParamDTO payParamDTO = new WeChatPayParamDTO();
		payParamDTO.setPayWay(EPayWay.PAY_WAY_APPC_WEIXINPAY);
		payParamDTO.setPayFrom(EPayFrom.PAY_FROM_APP_DOWN_VIDEO);
		payParamDTO.setNotifyUrl("http://176d3600b7.51mypc.cn/index.php/Wechat/Pay/weChatPayNotify");
		payParamDTO.setReturnUrl("");
		payParamDTO.setTradeNo("201510160000000555549789116");
		payParamDTO.setTotalFee(BigDecimal.valueOf(0.01));
		payParamDTO.setBody("分享购买商品");
		payParamDTO.setSpbillCreateIp("125.85.185.128");
		try{
			String string =payFacade.createWeiXinAppPay(payParamDTO);
			System.out.println(string);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void weiXinJsPayResponseValidator(){
		String requestParams="{\"result\":\"get_brand_wcpay_request:ok\",\"orderPayNo\":\"MALL_201510160000000555549789114\"}";
		payResponseValidatorFacade.weiXinJZHPayResponseValidator(requestParams);
	}
	
	
	@Test
	public void weiXinAppPayResponseValidator() throws InterruptedException{
		String requestParams="{\"result\":\"0\",\"orderPayNo\":\"MALL_201510160000000555549789114\"}";
		payResponseValidatorFacade.weiXinAppPayResponseValidator(requestParams);
		Thread.sleep(10000L);
	}
	
	/**
	 * 定时任务执行方法
	 * 
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月18日
	 * @records <p>  fushun 2017年1月18日</p>
	 */
	@Test
	public void getOnlinePayOrderJobExecute(){
		RecordPayDTO orderDTO=new RecordPayDTO();
		orderDTO.setOrderPayNo("2017011700000007090745");
		jobPayFacade.getOnlinePayOrderJobExecute(orderDTO);
	}
	
	
	/**
	 * 定时任务获取数据方法
	 * 
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月18日
	 * @records <p>  fushun 2017年1月18日</p>
	 */
	@Test
	public void getOnlinePayOrderJob(){
		jobPayFacade.getOnlinePayOrderJob(2, 5);
	}


	/**
	 * 微信公众号测试
	 */
	@Test
	public void mallWeiXinJZHPayNotify(){
		String str="{\"transaction_id\":\"4007162001201706206558614585\",\"nonce_str\":\"22vxq36uwalg5pvtbwiteczwwpe11hts\",\"bank_type\":\"ABC_DEBIT\",\"openid\":\"owR_esx7QGFQ1oogqrm197DlWYyg\",\"sign\":\"7EBA51F738AFA61773674BAF19EE7F50\",\"fee_type\":\"CNY\",\"mch_id\":\"1361997202\",\"cash_fee\":\"200\",\"device_info\":\"WEB\",\"out_trade_no\":\"MALL_2017062000000004164691\",\"appid\":\"wxb6df40b83166d473\",\"total_fee\":\"200\",\"trade_type\":\"JSAPI\",\"result_code\":\"SUCCESS\",\"time_end\":\"20170620100910\",\"is_subscribe\":\"Y\",\"return_code\":\"SUCCESS\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map= JsonUtil.jsonToMap(str, HashMap.class,String.class,String.class);
		notifyPayFacade.weiXinJZHPayNotify(map);
	}
	

}
