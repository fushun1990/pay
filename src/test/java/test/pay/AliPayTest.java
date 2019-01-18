package test.pay;

import cn.kidtop.business.pay.PayApplication;
import cn.kidtop.business.pay.dto.CallBackDTO;
import cn.kidtop.business.pay.dto.PayParamDTO;
import cn.kidtop.business.pay.dto.RefundParamDTO;
import cn.kidtop.business.pay.enumeration.EPayFrom;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERefundFrom;
import cn.kidtop.business.pay.facade.CallBack;
import cn.kidtop.business.pay.facade.PayFacade;
import cn.kidtop.business.pay.facade.PayResponseValidatorFacade;
import cn.kidtop.business.pay.facade.RefundFacade;
import cn.kidtop.framework.util.JsonUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayApplication.class)
public class AliPayTest{

	@Autowired
	private PayFacade  payFacade;
	@Autowired
	private RefundFacade refundFacade;
	@Autowired
	private PayResponseValidatorFacade payResponseValidatorFacade;
	
	@BeforeClass
	public static void postConstuct() {
		CallBack.registerCallBack(EPayFrom.PAY_FROM_APP_DOWN_VIDEO, AliPayTest::updatePayStatusSuccess,
				AliPayTest::updatePayStatusFailed);
		CallBack.registerCallBack(ERefundFrom.REFUND_FROM_APP_BUY_GOODS, AliPayTest::updatePayStatusSuccess,
				AliPayTest::updatePayStatusFailed);
		CallBack.registerCallBack(EPayFrom.PAY_FROM_WEB_DOWN_VIDEO, AliPayTest::updatePayStatusSuccess,
				AliPayTest::updatePayStatusFailed);
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
	public void testPay()
	{
		PayParamDTO payParamDTO = new PayParamDTO();
		payParamDTO.setPayWay(EPayWay.PAY_WAY_ALIPAY);
		payParamDTO.setPayFrom(EPayFrom.PAY_FROM_WEB_DOWN_VIDEO);
		payParamDTO.setNotifyUrl("");
		payParamDTO.setReturnUrl("");
		payParamDTO.setSubject("");
		payParamDTO.setTradeNo("20151016000000055");
		payParamDTO.setTotalFee(BigDecimal.valueOf(100));
		payFacade.createAlipayDirectPayResult(payParamDTO);
	}
	
	
	@Test
	public void testAppPay(){
		PayParamDTO payParamDTO = new PayParamDTO();
		payParamDTO.setPayWay(EPayWay.PAY_WAY_ALIPAY);
		payParamDTO.setPayFrom(EPayFrom.PAY_FROM_APP_DOWN_VIDEO);
		payParamDTO.setNotifyUrl("http://f.superisong.com/Home/pay/payNotice");
		payParamDTO.setReturnUrl("");
		payParamDTO.setTradeNo("2015101600000005555555");
		payParamDTO.setTotalFee(BigDecimal.valueOf(0.1));
		payParamDTO.setSubject("支付测试");
		String str=payFacade.createAlipayAppPayResult(payParamDTO);
		System.out.println(str);
		HashMap<String,Object> map= JsonUtil.jsonToHashMap(str);
		Assert.assertNotNull(map.get("orderPayNo"));
		Assert.assertNotNull(map.get("payForm"));
	}
	
	/**
	 * 支付宝 wap 支付
	 * 
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	@Test
	public void testAppWapPay(){
		PayParamDTO payParamDTO = new PayParamDTO();
		payParamDTO.setPayWay(EPayWay.PAY_WAY_ALIPAY);
		payParamDTO.setPayFrom(EPayFrom.PAY_FROM_APP_DOWN_VIDEO);
		payParamDTO.setNotifyUrl("http://f.superisong.com/Home/pay/payNotice");
		payParamDTO.setReturnUrl("http://f.superisong.com/Home/pay/payNotice");
		payParamDTO.setTradeNo("20151016000000455566");
		payParamDTO.setTotalFee(BigDecimal.valueOf(0.1));
		payParamDTO.setSubject("支付测试");
		String str=payFacade.createAlipayWapResult(payParamDTO);
		System.out.println(str);
		Assert.assertNotNull(str);
	}
	
	public void tet(){
		//		?total_amount=0.10&timestamp=2017-01-07 16:46:09&sign=hsptyzwDjc64x386jhc11o7JJdk/3eyYw0sb/cSm/AZffTZZB4BmfY2cEPI/JdDmKxb206mGxIPLWRD4htvNHsh60nN3R0YFlVmSqCTbBNSlpEwF47pj+NsmnztE2Ldc4YUGkA/kWqeM1SSlyQWsR4mZBt9wVpXL30uvuFMSheQ=&trade_no=2017010721001004060200066663&sign_type=RSA&auth_app_id=2016080100137922&charset=utf-8&seller_id=2088102169388233&method=alipay.trade.wap.pay.return&app_id=2016080100137922&out_trade_no=MALL_201510160000004555&version=1.0
	}
	
	
	@Test
	public void refund(){
		
		List<String> list=new ArrayList<String>();
		list.add("MALL_2016121100000023410083");
		list.add("MALL_2016121100000021422160");
//		list.add("MALL_2016120500000017853652");
//		list.add("MALL_2016120500000013505498");
//		list.add("MALL_2016120500000012322119");
//		list.add("MALL_2016120500000006589434");
//		list.add("MALL_2016120800000003711067");
//		list.add("MALL_2016120700000006450985");
//		list.add("MALL_2016120700000004561391");
//		list.add("MALL_2016120700000001501505");
		
		for (String string : list) {
			
			RefundParamDTO refundParamDTO=new RefundParamDTO();
			refundParamDTO.setOutTradeNo(string.replace("MALL_", ""));
			refundParamDTO.setRefundFrom(ERefundFrom.REFUND_FROM_APP_BUY_GOODS);
			refundParamDTO.setRefundMoney(BigDecimal.valueOf(0.04));
			refundParamDTO.setRefundNo(string);
			refundParamDTO.setRefundReason("拒收 取消交易");
			try{
				refundFacade.refundAppAlipayRefund(refundParamDTO);
			}catch (Exception e) {
			}
		}
		
	}
	
	/**
	 * 
	 * 
	 * @author fushun
	 * @version dev706
	 * @creation 2017年6月2日
	 * @records <p>  fushun 2017年6月2日</p>
	 */
	@Test
	public void appAliPayWapPayResponseValidator(){
		String str="{\"total_amount\":\"2.00\",\"timestamp\":\"2017-06-19 17:28:46\",\"sign\":\"LCMlt6LanySMhRO3M5UqoI10ymZXunX3PDp21IVYY/M0b9W+4t799QPcWXiU25t/J4d657xRqDthf2ZhNjJxNeEU2ueGWsScq9QZFA07YACoOoOSPe0Rf36XNo3KbHaWt9MbAU8EXs3hM2Eeei4YLgJq8E1bkEM8iYpHTRlyWn4=\",\"trade_no\":\"2017061921001004590286735661\",\"sign_type\":\"RSA\",\"auth_app_id\":\"2016090401846459\",\"charset\":\"utf-8\",\"seller_id\":\"2088121110197834\",\"method\":\"alipay.trade.wap.pay.return\",\"app_id\":\"2016090401846459\",\"out_trade_no\":\"MALL_2017061900000030132747\",\"version\":\"1.0\"}";
		payResponseValidatorFacade.appAliPayWapPayResponseValidator(str);
	}

	@Test
	public void appAliPayResponseValidator(){
		String str="{\n  \"result\" : \"{\\n  \\\"result\\\" : \\\"\\\",\\n  \\\"resultStatus\\\" : \\\"6001\\\",\\n  \\\"memo\\\" : \\\"用户中途取消\\\"\\n}\",\n  \"orderPayNo\" : \"ADW_152017110900000005133344\"\n}";
		payResponseValidatorFacade.appAliPayResponseValidator(str);
	}
	
	

}
