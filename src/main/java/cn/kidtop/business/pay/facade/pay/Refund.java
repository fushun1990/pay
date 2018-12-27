package cn.kidtop.business.pay.facade.pay;

import java.util.function.Consumer;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.kidtop.business.pay.dto.CallBackDTO;
import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.dto.RecordPayExceptionMessageDTO;
import cn.kidtop.business.pay.dto.RecordRefundDTO;
import cn.kidtop.business.pay.dto.RefundParamDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERefundNotityStatus;
import cn.kidtop.business.pay.enumeration.ERefundStatus;
import cn.kidtop.business.pay.exception.RefundException;
import cn.kidtop.business.pay.facade.CallBack;
import cn.kidtop.business.pay.facade.RecordPayExceptionMessageFacade;
import cn.kidtop.business.pay.service.RecordPayService;
import cn.kidtop.business.pay.service.RecordRefundService;
import cn.kidtop.framework.util.EnumUtil;
import cn.kidtop.framework.util.ExceptionUtil;
import cn.kidtop.framework.util.JsonUtil;

@Component
public abstract class Refund {

	@Autowired
	private PayCallBack<CallBackDTO> payCallBack;
	@Autowired
	private RecordRefundService recordRefundService;
	@Autowired
	private RecordPayService recordPayService;
	@Autowired
	private RecordPayExceptionMessageFacade recordPayExceptionMessageFacade;
	
	/**
	 * 支付退款请求
	 * @param refundParamDTO
	 * @param recordRefundDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	protected abstract String refundRequest(RefundParamDTO refundParamDTO,RecordRefundDTO recordRefundDTO);
	
	
	
	/**
	 * 退款 创建表单的形式<br>
	 * 
	 * <p>退款成功，未通知，则调用通知接口，通知退款成功，并且抛出异常：new RefundException("已退款", null, RefundException.REFUND_FLUSH);
	 * 
	 * <p>退款成功，已通知，则直接抛出异常 new RefundException("已退款", null, RefundException.REFUND_FLUSH);
	 * 
	 * @param refundParamDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public String refundResult(RefundParamDTO refundParamDTO) {
		Consumer<CallBackDTO> updateRefundStatusSuccessConsumer=CallBack.getSuccess(refundParamDTO.getRefundFrom());
		RecordRefundDTO recordRefundDTO=new RecordRefundDTO();
		createRefund(refundParamDTO,recordRefundDTO);
		
		EPayWay ePayWay=EnumUtil.getEnum(EPayWay.class,recordRefundDTO.getPayWay());
		CallBackDTO callBackDTO=new CallBackDTO( recordRefundDTO.getRefundNo(), ePayWay);
		
		
		ERefundStatus eRefundStatus=EnumUtil.getEnum(ERefundStatus.class, recordRefundDTO.getStatus());
		if(eRefundStatus==ERefundStatus.success){
			if(ERefundNotityStatus.No.getCode().equals(recordRefundDTO.getNoticeStatus())){
				payCallBack.callBack(updateRefundStatusSuccessConsumer, callBackDTO);
				recordRefundDTO.setNoticeStatus(ERefundNotityStatus.Yes.getCode());
				recordRefundService.updateRecordRefundNoticeStatus(recordRefundDTO);
				throw new RefundException(RefundException.Enum.OVER_REFUND);
			}
		}
		return refundRequest(refundParamDTO, recordRefundDTO);
	}
	
	/**
	 * 退款 服务器直接退款
	 * 
	 *<p>退款成功，未通知，则调用通知接口，通知退款成功，返回
	 * 
	 *<p>退款成功，已通知，则直接抛出异常 new RefundException("已退款", null, RefundException.REFUND_FLUSH);
	 * @param refundParamDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void refund(RefundParamDTO refundParamDTO) {
		Consumer<CallBackDTO> updateRefundStatusSuccessConsumer=CallBack.getSuccess(refundParamDTO.getRefundFrom());
		Consumer<CallBackDTO> updateRefundStatusFailConsumer=CallBack.getFail(refundParamDTO.getRefundFrom());
		RecordRefundDTO recordRefundDTO=new RecordRefundDTO();;
		createRefund(refundParamDTO,recordRefundDTO);
		
		EPayWay ePayWay=EnumUtil.getEnum(EPayWay.class,recordRefundDTO.getPayWay());
		CallBackDTO callBackDTO=new CallBackDTO( recordRefundDTO.getRefundNo(), ePayWay);
		
		if(ERefundStatus.success.getCode().equals(recordRefundDTO.getStatus())){
			if(ERefundNotityStatus.No.getCode().equals(recordRefundDTO.getNoticeStatus())){
				payCallBack.callBack(updateRefundStatusSuccessConsumer, callBackDTO);
				recordRefundDTO.setNoticeStatus(ERefundNotityStatus.Yes.getCode());
				recordRefundService.updateRecordRefundNoticeStatus(recordRefundDTO);
				return ;
			}
		}
		try{
			//执行退款
			refundRequest(refundParamDTO, recordRefundDTO);
			//退款成功，更新退款成功
			recordRefundDTO.setStatus(ERefundStatus.success.getCode());
			recordRefundDTO.setResult("success");
			recordRefundService.updateRecordRefund(recordRefundDTO);
		}catch (Exception e) {
	
			try{
				//验证失败，修改支付状态是失败
				recordRefundDTO.setStatus(ERefundStatus.fail.getCode());
				recordRefundDTO.setResult(e.getMessage());
				recordRefundService.updateRecordRefund(recordRefundDTO);

				//记录错误信息
				RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO=new RecordPayExceptionMessageDTO();
				superisongCommonRecordPayExceptionMessageDTO.setMethodName(this.getClass().getName()+":refund");
				superisongCommonRecordPayExceptionMessageDTO.setMethodParams(JsonUtil.classToJson(refundParamDTO));
				superisongCommonRecordPayExceptionMessageDTO.setErrorMessage(ExceptionUtil.getPrintStackTrace(e));
				recordPayExceptionMessageFacade.save(superisongCommonRecordPayExceptionMessageDTO);
			}catch(Exception e1){
			}
			callBackDTO.setMessage(e.getMessage());
			payCallBack.callBack(updateRefundStatusFailConsumer, callBackDTO);
			throw new PayException(e, PayException.Enum.BASECODE_EXCEPTION);
		}
		payCallBack.callBack(updateRefundStatusSuccessConsumer, callBackDTO);
		recordRefundDTO.setNoticeStatus(ERefundNotityStatus.Yes.getCode());
		recordRefundService.updateRecordRefundNoticeStatus(recordRefundDTO);
	}
	
	/**
	 * 创建退款
	 * @param refundParamDTO
	 * @param recordRefundDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	private void createRefund(RefundParamDTO refundParamDTO,RecordRefundDTO recordRefundDTO){
		RecordPayDTO recordPayDTO = recordPayService.getRecordPayByOutTradeNoAndPayWay(refundParamDTO.getRefundFrom().getPreStr()
				+ refundParamDTO.getOutTradeNo());

		if (recordPayDTO == null) {
			throw new PayException(null, PayException.Enum.PAY_ORDERNO_EXISTS_EXCEPTION);
		}
		recordRefundDTO.setOutTradeNo(recordPayDTO.getOutTradeNo());
		recordRefundDTO.setPayWay(recordPayDTO.getPayWay());
		recordRefundDTO.setRefundFrom(refundParamDTO.getRefundFrom().getCode());
		recordRefundDTO.setRefundMoney(refundParamDTO.getRefundMoney());
		recordRefundDTO.setRefundNo(refundParamDTO.getRefundNo());
		recordRefundDTO.setRefundReason(refundParamDTO.getRefundReason());
		recordRefundDTO.setStatus(ERefundStatus.wait.getCode());
		recordRefundDTO.setNoticeStatus(ERefundNotityStatus.No.getCode());
		
		//获取是否存在退款信息
		RecordRefundDTO recordRefundDTO2=recordRefundService.query(recordRefundDTO);
		ERefundStatus eRefundStatus=null;
		
		if(recordRefundDTO2!=null){
			//存在退款 
			recordRefundDTO=recordRefundDTO2;
			eRefundStatus=EnumUtil.getEnum(ERefundStatus.class, recordRefundDTO.getStatus());
			if(eRefundStatus==ERefundStatus.wait){
				throw new PayException(null, PayException.Enum.REFUNDING_EXCEPTION);
			}
			if(ERefundNotityStatus.Yes.getCode().equals(recordRefundDTO.getNoticeStatus())){
				throw new PayException(null, PayException.Enum.REFUNDED_EXCEPTION);
			}
			
		}
		if(recordRefundDTO2==null){
			//不存在退款，保存退款信息
			recordRefundService.saveRecordRefund(recordRefundDTO);
		}
	}
}
