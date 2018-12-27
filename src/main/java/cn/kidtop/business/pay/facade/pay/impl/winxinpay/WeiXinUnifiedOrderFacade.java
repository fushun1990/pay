package cn.kidtop.business.pay.facade.pay.impl.winxinpay;

import java.math.BigDecimal;

import cn.kidtop.business.pay.exception.PayException;
import cn.kidtop.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.kidtop.business.pay.dto.PayParamDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.dto.WeChatPayParamDTO;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.listener.AResultListener;
import cn.kidtop.framework.exception.BusinessException;

import com.tencent.WXPay;
import com.tencent.common.AppCConfigure;
import com.tencent.common.GZHConfigure;
import com.tencent.protocol.oauth20_protocol.OAuth20ResData;
import com.tencent.protocol.unifiedorder_protocol.AppUnifiedOrderReqData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderReqData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;

/**
 * 统一下单接口
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2017年1月4日
 */
@Service
public class WeiXinUnifiedOrderFacade {
	
	@Autowired
	private WeiXinOAuth20Facade weiXinOAuth20Facade;
	
	/**
	 *转换为分的常量
	 */
	public static BigDecimal bai= BigDecimal.valueOf(100);
	
	private AppUnifiedOrderReqData getReq(AppUnifiedOrderReqData unifiedorderReqData,PayParamDTO payParamDTO){
		String outTradeNo=payParamDTO.getPayFrom().getPreStr()+payParamDTO.getTradeNo();
		unifiedorderReqData.setBody(payParamDTO.getBody());
		unifiedorderReqData.setOut_trade_no(outTradeNo);
		unifiedorderReqData.setTotal_fee( payParamDTO.getTotalFee().multiply(bai).intValue());
		unifiedorderReqData.setSpbill_create_ip(payParamDTO.getSpbillCreateIp());
		unifiedorderReqData.setNotify_url(payParamDTO.getNotifyUrl());
		return unifiedorderReqData;
	}
	
	/**
	 * 支付
	 * @param payParamDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月4日
	 * @records <p>  fushun 2017年1月4日</p>
	 */
	public UnifiedorderResData unifiedOrderPay(WeChatPayParamDTO payParamDTO){
		UnifiedOrderResultListener unifiedOrderResultListener=new UnifiedOrderResultListener();
		
		
		try {
			if(payParamDTO.getPayWay()==EPayWay.PAY_WAY_WEIXINPAY){
				AppUnifiedOrderReqData unifiedorderReqData= new UnifiedorderReqData("WEB",GZHConfigure.initMethod());
				getReq(unifiedorderReqData,payParamDTO);
				String openId = "";
				//判断是否存在openId
				if(StringUtils.isEmpty(payParamDTO.getOpenId())){
					if(StringUtils.isEmpty(payParamDTO.getWeiXinAuthCode())){
						throw new PayException(PayException.Enum.WECHAT_CODE_NOT_NULL_EXCEPTION);
					}
					OAuth20ResData oAuth20ResData= weiXinOAuth20Facade.getOAuth20Data(payParamDTO.getWeiXinAuthCode());
					openId=oAuth20ResData.getOpenid();
				}else{
					openId = payParamDTO.getOpenId();
				}
				((UnifiedorderReqData)unifiedorderReqData).setOpenid(openId);
				unifiedorderReqData.setTrade_type("JSAPI");
				WXPay.unifiedorderBusiness((UnifiedorderReqData)unifiedorderReqData, unifiedOrderResultListener);
			}
			if(payParamDTO.getPayWay()==EPayWay.PAY_WAY_APPC_WEIXINPAY){
				AppUnifiedOrderReqData unifiedorderReqData= new AppUnifiedOrderReqData("WEB",AppCConfigure.initMethod());
				getReq(unifiedorderReqData,payParamDTO);
				unifiedorderReqData.setTrade_type("APP");
				WXPay.unifiedorderBusiness(unifiedorderReqData, unifiedOrderResultListener);
			}
			UnifiedorderResData resData=unifiedOrderResultListener.getResData();
			return resData;
		} catch (BusinessException e) {
			throw new PayException(new Throwable(e.getMessage()), PayException.Enum.PAY_FAILED_EXCEPTION);
		}catch (Exception e) {
			throw new PayException( e, PayException.Enum.PAY_FAILED_EXCEPTION);
		}
	}
	
	/**
	 * 统一下单，回调接口
	 * @author fushun
	 *
	 * @version V3.0商城
	 * @creation 2017年1月4日
	 */
	private class UnifiedOrderResultListener extends AResultListener<UnifiedorderResData>{

		private UnifiedorderResData unifiedorderResData=null;
		
		@Override
		public UnifiedorderResData getResData() {
			return unifiedorderResData;
		}

		@Override
		public void onSuccess(UnifiedorderResData t) {
			this.unifiedorderResData=t;
		}
		
		
	}
}
