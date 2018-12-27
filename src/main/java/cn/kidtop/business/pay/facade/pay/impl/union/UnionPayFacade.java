package cn.kidtop.business.pay.facade.pay.impl.union;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.kidtop.business.pay.dto.NotifyReturnDTO;
import cn.kidtop.business.pay.dto.PayParamDTO;
import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.facade.pay.Pay;
import cn.kidtop.business.pay.facade.sdk.unionpaysdk.AcpService;
import cn.kidtop.business.pay.facade.sdk.unionpaysdk.SDKConfig;
import cn.kidtop.business.pay.facade.sdk.unionpaysdk.config.UnionpayConfig;

/**
 * 银联支付 接口
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2016年9月10日
 */
@Component
public class UnionPayFacade extends Pay<PayParamDTO>{

	@Override
	public Map<String, String> getRequestData(PayParamDTO payParamDTO) {
		String outTradeNo = payParamDTO.getTradeNo();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.put("version", UnionpayConfig.UNIONPAY_VERSION);
		requestData.put("encoding", UnionpayConfig.ENCODING_UTF8);
		requestData.put("signMethod", UnionpayConfig.UNIONPAY_SIGN_METHOD_RSA); 
		requestData.put("txnType", UnionpayConfig.UNIONPAY_TXN_TYPE_CONSUME); 
		requestData.put("txnSubType", UnionpayConfig.UNIONPAY_TXN_TYPE_BUFFET_CONSUME);
		requestData.put("bizType", UnionpayConfig.UNIONPAY_BIZ_TYPE);
		requestData.put("channelType", UnionpayConfig.UNIONPAY_CHANNEL_TYPE);

		/*** 商户接入参数 ***/
		requestData.put("merId", UnionpayConfig.UNIONPAY_MERID); 
		requestData.put("accessType", "0");
		requestData.put("orderId", outTradeNo); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		requestData.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		requestData.put("currencyCode", "156"); 
		requestData.put("txnAmt", payParamDTO.getTotalFee().multiply(BigDecimal.valueOf(100)).toPlainString()); // 交易金额，单位分，不要带小数点
		requestData.put("frontUrl", payParamDTO.getNotifyUrl());//成功后返回url
		requestData.put("backUrl", payParamDTO.getReturnUrl());//回调url
		return requestData;
	}

	@Override
	public String createPayHtml(Map<String, String> map) {
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		Map<String, String> submitFromData = AcpService.sign(map,UnionpayConfig.ENCODING_UTF8);  
		//获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl(); 
		//生成自动跳转的Html表单
		String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,UnionpayConfig.ENCODING_UTF8);  
		return html;
	}

	@Override
	public void payNotifyAlipayReust(Map<String, String> requestParams, RecordPayDTO recordPayDTO) {
		// TODO Auto-generated method stub
		return ;
	}

	@Override
	protected NotifyReturnDTO getNotifyReturn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EPayWay getPayWay() {
		return EPayWay.PAY_WAY_UNIONPAY;
	}
}
