package cn.kidtop.business.pay.facade.pay.impl.alipay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.kidtop.business.pay.dto.NotifyReturnDTO;
import cn.kidtop.business.pay.dto.PayParamDTO;
import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERecordPayStatus;
import cn.kidtop.business.pay.facade.pay.Pay;
import cn.kidtop.business.pay.facade.sdk.alipay.config.AlipayConfig;
import cn.kidtop.business.pay.facade.sdk.alipay.enumeration.ETradeStatus;
import cn.kidtop.business.pay.facade.sdk.alipay.util.AlipayNotify;
import cn.kidtop.business.pay.facade.sdk.alipay.util.AlipaySubmitApp;
import cn.kidtop.framework.constant.SystemConstant;
import cn.kidtop.framework.util.DESUtil;
import cn.kidtop.framework.util.EnumUtil;

/**
 * 支付宝即时到账支付接口
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2016年9月13日
 */
@Component
public class AlipayDirectPayFacade extends Pay<PayParamDTO>{

	@Autowired
	private AlipayConfig alipayConfig;

	/**
	 * 返回对象
	 */
	private static NotifyReturnDTO notifyReturnDTO;
	
	static{
		notifyReturnDTO=new NotifyReturnDTO();
		notifyReturnDTO.setFail("fail");
		notifyReturnDTO.setSuccess("success");
	}
	
	@Override
	protected Map<String, String> getRequestData(PayParamDTO payParamDTO) {
		String outTradeNo = payParamDTO.getPayFrom().getPreStr() + payParamDTO.getTradeNo();

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		
		
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", alipayConfig.getPartner());
        sParaTemp.put("seller_email", alipayConfig.getSellerEmail());
        sParaTemp.put("_input_charset", alipayConfig.getInputCharset());
		sParaTemp.put("payment_type", "1");//只支持取值为1（商品购买）。
		sParaTemp.put("notify_url", payParamDTO.getNotifyUrl());
		sParaTemp.put("return_url", payParamDTO.getReturnUrl());
		sParaTemp.put("out_trade_no", outTradeNo);
		sParaTemp.put("subject", payParamDTO.getSubject());
		sParaTemp.put("total_fee", payParamDTO.getTotalFee().toPlainString());
		
		return sParaTemp;
	}

	@Override
	protected String createPayHtml(Map<String, String> map) {
		return AlipaySubmitApp.buildRequest(map,alipayConfig.getRsaPrivateKeyPkcs8());
	}

    
	
    
	@Override
	protected void payNotifyAlipayReust(Map<String, String> requestParams,RecordPayDTO recordPayDTO) {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : requestParams.entrySet()) {
			// String name = (String) iter.next();
			// String values = (String) requestParams.get(name);
			// String valueStr = "";
			// for (int i = 0; i < values.length; i++) {
			// valueStr = (i == values.length - 1) ? valueStr + values[i]
			// : valueStr + values[i] + ",";
			// }
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(entry.getKey(), entry.getValue());
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = params.get("out_trade_no").toString();

		// 支付宝交易号
		String trade_no = params.get("trade_no").toString();

		// 交易状态
		String trade_status = params.get("trade_status").toString();

		String seller_email = params.get("seller_email").toString();
		String buyer_email = params.get("buyer_email").toString();
		float total_fee = Float.valueOf(requestParams.get("total_fee").toString());

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if (!AlipayNotify.verify(params,alipayConfig.getInputCharset())) {
			throw new PayException(PayException.Enum.SIGNATURE_VALIDATION_FAILED_EXCEPTION);
		}
		// 验证成功
		// ////////////////////////////////////////////////////////////////////////////////////////
		// 请在这里加上商户的业务逻辑程序代码

		recordPayDTO.setOutTradeNo(out_trade_no);
		recordPayDTO.setPayNo(trade_no);
		recordPayDTO.setPayMoney(BigDecimal.valueOf(total_fee));
		try {
			recordPayDTO.setReceiveAccourt(DESUtil.encrypt(seller_email, SystemConstant.DES_KEY));
			recordPayDTO.setPayAccount(DESUtil.encrypt(buyer_email, SystemConstant.DES_KEY));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ETradeStatus tradeStatus= EnumUtil.getEnum(ETradeStatus.class, 	trade_status);
		if(tradeStatus==null){
			throw new PayException(null, PayException.Enum.ALIPAY_ORDER_STATUS_EXCEPTION);
		}
		switch (tradeStatus) {
			case WAIT_BUYER_PAY:
				recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());
				return ;
			case TRADE_CLOSED:
				recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());
				return ;
			case TRADE_SUCCESS:
				recordPayDTO.setStatus(ERecordPayStatus.success.getCode());
				return ;
			case TRADE_FINISHED:
				recordPayDTO.setStatus(ERecordPayStatus.success.getCode());
				return ;
			default:
				break;
		}

		throw new PayException(null, PayException.Enum.PAY_BUSINESS_EXCEPTION);
		
	}

	@Override
	protected NotifyReturnDTO getNotifyReturn() {
		return AlipayDirectPayFacade.notifyReturnDTO;
	}

	@Override
	protected EPayWay getPayWay() {
		return EPayWay.PAY_WAY_ALIPAY;
	}
}
