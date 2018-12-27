package cn.kidtop.business.pay.facade.notity;

import java.util.function.Consumer;

import cn.kidtop.business.pay.exception.PayException;
import cn.kidtop.business.pay.exception.PayNotityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.kidtop.business.pay.dto.CallBackDTO;
import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.dto.RecordPayExceptionMessageDTO;
import cn.kidtop.business.pay.enumeration.EPayFrom;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERecordPayNotityStatus;
import cn.kidtop.business.pay.enumeration.ERecordPayStatus;
import cn.kidtop.business.pay.facade.CallBack;
import cn.kidtop.business.pay.facade.RecordPayExceptionMessageFacade;
import cn.kidtop.business.pay.facade.pay.PayCallBack;
import cn.kidtop.business.pay.service.RecordPayService;
import cn.kidtop.framework.exception.BusinessException;
import cn.kidtop.framework.util.EnumUtil;
import cn.kidtop.framework.util.ExceptionUtil;
import cn.kidtop.framework.util.JsonUtil;

/**
 * 支付 通知
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2016年12月21日
 */
@Component
public class PayNotity {
	@Autowired
	private PayCallBack<CallBackDTO> payCallBack;
	@Autowired
	private RecordPayService recordPayService;
	@Autowired
	private RecordPayExceptionMessageFacade recordPayExceptionMessageFacade;
	
	
	/**
	 * 更新支付状态
	 * <p> 更新状态状态之后，通知业务系统，更新支付状态（成功/失败）。通知返回之后，更新通知状态。
	 * <P>通知业务出现错误，记录支付异常日志
	 * @param recordPayDTO
	 * @param payWay
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月17日
	 * @records <p>  fushun 2016年9月17日</p>
	 */
	public void updatePayStatus(RecordPayDTO recordPayDTO,EPayWay payWay){
		
		recordPayDTO.setPayWay(payWay.getCode());
		
		recordPayDTO.setReceiveWay(payWay.getCode());
		
		//更新支付记录状态，并返回新对象
		recordPayDTO = recordPayService.updatePayStatus(recordPayDTO);
		notity(recordPayDTO, payWay);
	}
	
	/**
	 * 通知支付成功/失败
	 * <P>通知业务出现错误，记录支付异常日志
	 * @param recordPayDTO
	 * @date: 2017-09-20 23:59:29
	 * @author:wangfushun
	 * @version 1.0
	 */
	public void notity(RecordPayDTO recordPayDTO,EPayWay payWay) {
		String payFrom=recordPayDTO.getPayFrom();
		EPayFrom ePayFrom= EnumUtil.getEnum(EPayFrom.class,payFrom);
		if(ePayFrom==null){
			throw new PayNotityException(null, PayNotityException.Enum.PAYENT_ERROR);
		}
		Consumer<CallBackDTO> updatePayStatusSuccessConsumer=CallBack.getSuccess(ePayFrom);
		Consumer<CallBackDTO> updatePayStatusFailConsumer=CallBack.getFail(ePayFrom);
		
		
		//判断是否已系统通知，已通知，则不再处理
		if(recordPayDTO.getStatus().equals(ERecordPayStatus.success.getCode()) && 
				EnumUtil.getEnum(ERecordPayNotityStatus.class, recordPayDTO.getNotityStatus())==ERecordPayNotityStatus.yes ){
			return ;
		}
		try{
			CallBackDTO callBackDTO=new CallBackDTO(recordPayDTO.getOrderPayNo(), payWay);
			if (ERecordPayStatus.success.getCode().equals(recordPayDTO.getStatus())) {
				payCallBack.callBack(updatePayStatusSuccessConsumer,callBackDTO);
				recordPayDTO.setNotityStatus(ERecordPayNotityStatus.yes.getCode());
				recordPayService.updateNotityStatus(recordPayDTO);
			} else {
				payCallBack.callBack(updatePayStatusFailConsumer,callBackDTO);
				recordPayDTO.setNotityStatus(ERecordPayNotityStatus.yes.getCode());
				recordPayService.updateNotityStatus(recordPayDTO);
			}
		}catch (Exception e) {
			try{
				RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO=new RecordPayExceptionMessageDTO();
				superisongCommonRecordPayExceptionMessageDTO.setMethodName(this.getClass().getName()+":updatePayStatus");
				superisongCommonRecordPayExceptionMessageDTO.setMethodParams(JsonUtil.classToJson(recordPayDTO));
				superisongCommonRecordPayExceptionMessageDTO.setErrorMessage(ExceptionUtil.getPrintStackTrace(e));
				recordPayExceptionMessageFacade.save(superisongCommonRecordPayExceptionMessageDTO);
			}catch (Exception e2) {}
			
			//如果是业务异常 抛出源异常
			if(e instanceof BusinessException) {
				throw e;
			}
			//如果是系统异常，抛出支付错误异常
			throw new PayException(e,PayException.Enum.BASECODE_EXCEPTION);
		}
	}

}
