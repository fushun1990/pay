package cn.kidtop.business.pay.facade.pay.impl.winxinpay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.kidtop.business.pay.dto.NotifyReturnDTO;
import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERecordPayStatus;
import cn.kidtop.business.pay.facade.pay.Pay;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.dto.WeChatPayParamDTO;
import cn.kidtop.framework.util.DateUtil;
import cn.kidtop.framework.util.JsonUtil;

import com.tencent.common.AppCConfigure;
import com.tencent.common.Signature;
import com.tencent.protocol.apppay_protocol.AppPayReqData;
import com.tencent.protocol.base_protocol.BaseReqData;
import com.tencent.protocol.jspay_protocol.NotifyResData;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;

/**
 * 微信app支付
 * @author fushun
 *
 * @version devlogin
 * @creation 2017年5月27日
 */
@Component
public class WeChatAppPayFacade extends Pay<WeChatPayParamDTO>{
	@Autowired
	private WeiXinUnifiedOrderFacade weiXinUnifiedOrderFacade;
	@Autowired
	private WeiXinPayQueryFacade weiXinPayQueryFacade;
	
	/**
	 * 返回对象
	 */
	private static NotifyReturnDTO notifyReturnDTO;
	
	static{
		notifyReturnDTO=new NotifyReturnDTO();
		notifyReturnDTO.setFail("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		notifyReturnDTO.setSuccess("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
	}
	/**
	 * 参数
	 * @param payParamDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月4日
	 * @records <p>  fushun 2017年1月4日</p>
	 */
	@Override
	protected Map<String, String> getRequestData(WeChatPayParamDTO payParamDTO) {
		String outTradeNo=payParamDTO.getPayFrom().getPreStr()+payParamDTO.getTradeNo();
		UnifiedorderResData unifiedorderResData=null;
		unifiedorderResData= weiXinUnifiedOrderFacade.unifiedOrderPay(payParamDTO);
		
		AppPayReqData jsPayReqData=new AppPayReqData(AppCConfigure.initMethod());
		jsPayReqData.setTimestamp(String.valueOf(DateUtil.getTimestamp()/1000));
		jsPayReqData.setPackage_("Sign=WXPay");
		jsPayReqData.setPrepayid(unifiedorderResData.getPrepay_id());
		jsPayReqData.setSign(Signature.getMD5Sign(Signature.getSignMap(AppPayReqData.class, jsPayReqData),jsPayReqData.getConfigure()));
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("appId", jsPayReqData.getAppid());
		map.put("partnerid", jsPayReqData.getPartnerid());
		map.put("prepayid", jsPayReqData.getPrepayid());
		map.put("package", jsPayReqData.getPackage_());
		map.put("noncestr", jsPayReqData.getNoncestr());
		map.put("timestamp", jsPayReqData.getTimestamp());
		map.put("sign", jsPayReqData.getSign());
		map.put("orderPayNo", outTradeNo);
		return map;
	}
	
	protected Map<String,String> setRequestData(BaseReqData baseReqData,UnifiedorderResData unifiedorderResData){
		
		return null;
	}

	@Override
	protected String createPayHtml(Map<String, String> map) {
		return JsonUtil.toJson(map);
	}

	@Override
	protected void payNotifyAlipayReust(Map<String, String> requestParams, RecordPayDTO recordPayDTO) {
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.putAll(requestParams);
		if (!Signature.checkIsSignValidFromResponseString(map, AppCConfigure.initMethod())) {
			throw new PayException(PayException.Enum.SIGNATURE_VALIDATION_FAILED_EXCEPTION);
		}
		NotifyResData notifyResData=JsonUtil.hashMapToClass(map, NotifyResData.class);
		if("FAIL".equals(notifyResData.getReturn_code())){
			recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());
			return ;
		}
		recordPayDTO.setPayNo(notifyResData.getTransaction_id());
		recordPayDTO.setOutTradeNo(notifyResData.getOut_trade_no());
		recordPayDTO.setStatus(ERecordPayStatus.success.getCode());
		recordPayDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(notifyResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
	}

	@Override
	protected NotifyReturnDTO getNotifyReturn() {
		return notifyReturnDTO;
	}


	/**
	 * 
	 * @param requestParams 返回参数 {"result":"微信返回参数","orderPayNo":"订单支付单号"}
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月6日
	 * @see cn.kidtop.pay.facade.pay.Pay#payResultAlipayReust(java.lang.String, cn.kidtop.pay.dto.RecordPayDTO)
	 */
	@Override
	protected void payResultAlipayReust(String requestParams, RecordPayDTO recordPayDTO) {
		Map<String,Object> map= JsonUtil.jsonToHashMap(requestParams);
		if(map==null || StringUtils.isEmpty(map.get("orderPayNo"))){
			throw new PayException(PayException.Enum.PAY_FAILED_EXCEPTION);
		}
		recordPayDTO.setOutTradeNo(map.get("orderPayNo").toString());
		recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());

		if(!"0".equals(map.get("result"))){
			throw new PayException(PayException.Enum.PAY_FAILED_EXCEPTION);
		}
		recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());
		
		OrderQueryResData orderQueryResData=weiXinPayQueryFacade.getOrderQuery(map.get("orderPayNo").toString(),AppCConfigure.initMethod());
		
		recordPayDTO.setPayNo(orderQueryResData.getTransaction_id());
		recordPayDTO.setOutTradeNo(orderQueryResData.getOut_trade_no());
		recordPayDTO.setStatus(ERecordPayStatus.success.getCode());
		recordPayDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(orderQueryResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
	}


	@Override
	protected EPayWay getPayWay() {
		return EPayWay.PAY_WAY_APPC_WEIXINPAY;
	}
}
