package cn.kidtop.business.pay.facade.pay.impl.winxinpay;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.kidtop.business.pay.dto.RecordRefundDTO;
import cn.kidtop.business.pay.dto.RefundParamDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERefundStatus;
import cn.kidtop.business.pay.facade.pay.Refund;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.enumeration.ERefundAccount;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.exception.WeiXinRefundException;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.listener.AResultListener;
import cn.kidtop.framework.exception.BusinessException;
import cn.kidtop.framework.util.EnumUtil;
import cn.kidtop.framework.util.JsonUtil;

import com.tencent.WXPay;
import com.tencent.common.AppCConfigure;
import com.tencent.common.GZHConfigure;
import com.tencent.protocol.refund_protocol.RefundReqData;
import com.tencent.protocol.refund_protocol.RefundResData;

/**
 * 微信退款 实现
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2017年1月6日
 */
@Component
public class WeiXinRefundFacade extends Refund{

	/**
	 * 微信退款
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @see cn.kidtop.pay.facade.pay.Refund#refundRequest(cn.kidtop.pay.dto.RefundParamDTO, cn.kidtop.pay.dto.RecordRefundDTO)
	 */
	@Override
	public String refundRequest(RefundParamDTO refundParamDTO, RecordRefundDTO recordRefundDTO) {
		RefundReqData refundReqData= getReq(refundParamDTO,recordRefundDTO);
		
		RefundResultListener resultListener=new RefundResultListener();
		
		try {
				WXPay.doRefundBusiness(refundReqData, resultListener);
				RefundResData refundResData = resultListener.getResData();
				recordRefundDTO.setPayNo(refundResData.getRefund_id());
				recordRefundDTO.setStatus(ERefundStatus.success.getCode());
				recordRefundDTO.setResult(JsonUtil.classToJson(refundResData));

		}catch (WeiXinRefundException e){
			//切换一种退款源方式，再次退款
			if(StringUtils.isEmpty(refundParamDTO.geteRefundAccount())){
				refundParamDTO.seteRefundAccount(ERefundAccount.REFUND_SOURCE_RECHARGE_FUNDS);
				return refundRequest(refundParamDTO,recordRefundDTO);
			}
			recordRefundDTO.setStatus(ERefundStatus.fail.getCode());
			throw e;
		}catch(BusinessException e){
			recordRefundDTO.setStatus(ERefundStatus.fail.getCode());
			throw e;
		} catch (Exception e) {
			recordRefundDTO.setStatus(ERefundStatus.fail.getCode());
			throw new PayException(e, PayException.Enum.REFUND_ERROR_EXCEPTION);
		}
		return null;
	}

	
	private RefundReqData getReq(RefundParamDTO refundParamDTO, RecordRefundDTO recordRefundDTO){
		RefundReqData refundReqData=null;
		EPayWay ePayWay=EnumUtil.getEnum(EPayWay.class, recordRefundDTO.getPayWay());
		switch (ePayWay) {
			case PAY_WAY_WEIXINPAY:
				refundReqData=new RefundReqData(null,GZHConfigure.initMethod());
				break;
			case PAY_WAY_APPC_WEIXINPAY:
				refundReqData=new RefundReqData(null,AppCConfigure.initMethod());
				break;
			default:
				break;
		};
		refundReqData.setOut_trade_no(refundParamDTO.getRefundFrom().getPreStr()+refundParamDTO.getOutTradeNo());
		refundReqData.setOut_refund_no(refundParamDTO.getRefundNo());
		refundReqData.setTotal_fee(refundParamDTO.getRefundMoney().multiply(WeiXinUnifiedOrderFacade.bai).intValue());
		refundReqData.setRefund_fee(refundParamDTO.getRefundMoney().multiply(WeiXinUnifiedOrderFacade.bai).intValue());

		if(!StringUtils.isEmpty(refundParamDTO.geteRefundAccount())){
			refundReqData.setRefund_account(refundParamDTO.geteRefundAccount().getCode());
		}

		return refundReqData;
	}
	
	private class RefundResultListener extends AResultListener<RefundResData>{
		private RefundResData refundResData;

		@Override
		public void onFail(RefundResData refundResData) {
			if("NOTENOUGH".equals(refundResData.getErr_code())){
				throw new PayException(new Throwable(refundResData.getErr_code_des()),PayException.Enum.REFUND_ERROR_EXCEPTION);
			}
			this.onFail(refundResData);
		}

		@Override
		public void onSuccess(RefundResData t) {
			this.refundResData=t;
		}

		@Override
		public RefundResData getResData() {
			return this.refundResData;
		}
		
	}
}
