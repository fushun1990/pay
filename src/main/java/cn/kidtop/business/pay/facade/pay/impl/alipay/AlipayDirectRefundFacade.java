package cn.kidtop.business.pay.facade.pay.impl.alipay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import cn.kidtop.business.pay.facade.sdk.alipay.util.AlipaySubmitWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.kidtop.business.pay.dto.CallBackDTO;
import cn.kidtop.business.pay.dto.RecordRefundDTO;
import cn.kidtop.business.pay.dto.RefundParamDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERefundFrom;
import cn.kidtop.business.pay.facade.CallBack;
import cn.kidtop.business.pay.facade.pay.PayCallBack;
import cn.kidtop.business.pay.facade.pay.Refund;
import cn.kidtop.business.pay.facade.sdk.alipay.config.AlipayConfig;
import cn.kidtop.business.pay.facade.sdk.alipay.util.AlipayNotify;
import cn.kidtop.business.pay.service.RecordRefundService;
import cn.kidtop.framework.util.DateUtil;
import cn.kidtop.framework.util.EnumUtil;


/**
 * 支付宝 即时到账  退款 接口
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2016年9月10日
 */
@Component
public class AlipayDirectRefundFacade extends Refund{
	
	@Autowired
	private RecordRefundService recordRefundService;
	@Autowired
	private PayCallBack<CallBackDTO> payCallBack;

	@Autowired
	private AlipayConfig alipayConfig;
	
	@Override
	protected String refundRequest(RefundParamDTO refundParamDTO, RecordRefundDTO recordRefundDTO) {
		Map<String, String> map= getRequestData(refundParamDTO);
		return createPayHtml(map);
	}
	
	
	
	/**
	 * 退款结果验证
	 * 
	 * @param requestParams
	 * @return
	 */
	@Transactional()
	public String refundNotifyAlipay(Map<String, Object> requestParams,EPayWay ePayWay) {
		
		RecordRefundDTO recordRefundDTO = new RecordRefundDTO();
		recordRefundDTO.setPayWay(ePayWay.getCode());
		boolean bool=payNotifyAlipayReust(requestParams, recordRefundDTO);
		
		recordRefundDTO = recordRefundService.updateRecordRefund(recordRefundDTO);
		ERefundFrom eRefundFrom=EnumUtil.getEnum(ERefundFrom.class, recordRefundDTO.getRefundFrom());
		Consumer<CallBackDTO> updatePayStatusSuccessConsumer=CallBack.getSuccess(eRefundFrom);
//		Consumer<CallBackDTO> updatePayStatusFailConsumer=CallBack.getFail(eRefundFrom);
		
		CallBackDTO callBackDTO=new CallBackDTO(recordRefundDTO.getRefundNo(), ePayWay);
		if(bool==false){
			payCallBack.callBack(updatePayStatusSuccessConsumer, callBackDTO);
			return "fail";
		}
		payCallBack.callBack(updatePayStatusSuccessConsumer, callBackDTO);
		return "success"; // 请不要修改或删除

	}
	
	
	protected Map<String, String> getRequestData(RefundParamDTO refundParamDTO) {
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
		sParaTemp.put("partner", alipayConfig.getPartner());
		sParaTemp.put("_input_charset", alipayConfig.getInputCharset());
		sParaTemp.put("notify_url", refundParamDTO.getNotifyUrl());
		sParaTemp.put("seller_email", alipayConfig.getSellerEmail());
		sParaTemp.put("refund_date", DateUtil.doDateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));
		sParaTemp.put("batch_no", refundParamDTO.getRefundNo());
		sParaTemp.put("batch_num", "1");
		sParaTemp.put("detail_data", refundParamDTO.getRefundNo() + "^" + refundParamDTO.getRefundMoney() + "^"
				+ refundParamDTO.getRefundReason());
		return sParaTemp;
	}

	protected String createPayHtml(Map<String, String> map) {
		// 建立请求
		return AlipaySubmitWeb.buildRequest(map, "get", "确认");
	}

	
	protected boolean payNotifyAlipayReust(Map<String,Object> requestParams,RecordRefundDTO recordRefundDTO) {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
			// String values = (String) requestParams.get(name);
			// String valueStr = "";
			// for (int i = 0; i < values.length; i++) {
			// valueStr = (i == values.length - 1) ? valueStr + values[i]
			// : valueStr + values[i] + ",";
			// }
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(entry.getKey(), entry.getValue().toString());
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 批次号
		String batch_no = params.get("batch_no").toString();

		// 批量退款数据中转账成功的笔数
		String success_num = params.get("success_num").toString();

		// 批量退款数据中的详细信息
		String result_details = params.get("result_details").toString();
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if (AlipayNotify.verify(params,alipayConfig.getInputCharset())) {
			return false;
		}
		
		// 验证成功
		// ////////////////////////////////////////////////////////////////////////////////////////
		// 请在这里加上商户的业务逻辑程序代码

		int count = Integer.parseInt(success_num);

		if (count == 1) {
			recordRefundDTO.setRefundNo(batch_no);
			recordRefundDTO.setResult(result_details);
			recordRefundDTO.setStatus(1);

			
		}
		// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

		// 判断是否在商户网站中已经做过了这次通知返回的处理
		// 如果没有做过处理，那么执行商户的业务程序
		// 如果有做过处理，那么不执行商户的业务程序

		return true; // 请不要修改或删除

		// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
	
	}

}
