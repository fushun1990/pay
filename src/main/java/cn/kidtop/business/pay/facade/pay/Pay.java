package cn.kidtop.business.pay.facade.pay;

import java.util.Map;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.kidtop.business.pay.dto.NotifyReturnDTO;
import cn.kidtop.business.pay.dto.PayParamDTO;
import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.dto.RecordPayExceptionMessageDTO;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERecordPayNotityStatus;
import cn.kidtop.business.pay.enumeration.ERecordPayStatus;
import cn.kidtop.business.pay.facade.RecordPayExceptionMessageFacade;
import cn.kidtop.business.pay.facade.notity.PayNotity;
import cn.kidtop.business.pay.service.RecordPayService;
import cn.kidtop.framework.exception.BusinessException;
import cn.kidtop.framework.util.EnumUtil;
import cn.kidtop.framework.util.ExceptionUtil;
import cn.kidtop.framework.util.JsonUtil;

@Component
public abstract class Pay<T extends PayParamDTO> {
	
	
	@Autowired
	private RecordPayService recordPayService;
	@Autowired
	private RecordPayExceptionMessageFacade recordPayExceptionMessageFacade;
	@Autowired
	private PayNotity payNotity;
	
	/**
	 * 支付订单
	 * @param payParamDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月10日
	 * @records <p>修改支付流程，已支付的，直接通知支付成功，然后提示已支付  fushun 2017年1月3日</p>
	 */
	@Transactional()
	public String pay(T payParamDTO){
		String outTradeNo=payParamDTO.getPayFrom().getPreStr()+payParamDTO.getTradeNo();
		RecordPayDTO recordPayDTO = new RecordPayDTO();
		recordPayDTO.setOutTradeNo(outTradeNo);
		recordPayDTO.setOrderPayNo(payParamDTO.getTradeNo());
		recordPayDTO.setPayMoney(payParamDTO.getTotalFee());
		recordPayDTO.setPayWay(payParamDTO.getPayWay().getCode());
		recordPayDTO.setPayFrom(payParamDTO.getPayFrom().getCode());
		recordPayDTO.setStatus(ERecordPayStatus.created.getCode());
		recordPayDTO.setNotityStatus(ERecordPayNotityStatus.no.getCode());
		
		RecordPayDTO recordPayDTO2=recordPayService.getRecordPayByOutTradeNoAndPayWay(recordPayDTO.getOutTradeNo());
		if(recordPayDTO2==null){
			recordPayService.saveRecordPay(recordPayDTO);
			return getRequest(payParamDTO,recordPayDTO);
		}
		
		ERecordPayStatus eRecordPayStatus= EnumUtil.getEnum(ERecordPayStatus.class, recordPayDTO2.getStatus());
		if(eRecordPayStatus!=ERecordPayStatus.success){
			recordPayService.updateRePayInfo(recordPayDTO);
			return getRequest(payParamDTO,recordPayDTO);
		}
		
		recordPayDTO=recordPayDTO2;
		ERecordPayNotityStatus eRecordPayNotityStatus=EnumUtil.getEnum(ERecordPayNotityStatus.class, recordPayDTO.getNotityStatus());
		if(eRecordPayStatus==ERecordPayStatus.success && eRecordPayNotityStatus==ERecordPayNotityStatus.yes){
			throw new PayException(PayException.Enum.PAY_SUCCESS_EXCEPTION);
		}
		
		if(eRecordPayStatus==ERecordPayStatus.success && eRecordPayNotityStatus==ERecordPayNotityStatus.no){
//			try{
				payNotity.notity(recordPayDTO, payParamDTO.getPayWay());
				throw new PayException(null, PayException.Enum.PAY_NOTITY_EXCEPTION);
//			}catch(Exception e){
//				
//				//如如果是业务异常  抛出源异常
//				if(e instanceof BusinessException) {
//					throw e;
//				}
//				//如果是系统异常，抛出错误异常
//				throw new PayException(e,PayException.Enum.BASECODE_EXCEPTION);
//			}
//			throw new PayException(null, PayException.Enum.PAY_SUCCESS_EXCEPTION);
		}
		
		throw new PayException(PayException.Enum.PAY_FAILED_EXCEPTION);
		
		
	}
	
	
	/**
	 * 获取支付参数
	 * @param payParamDTO
	 * @param recordPayDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月18日
	 * @records <p>  fushun 2017年1月18日</p>
	 */
	private String getRequest(T payParamDTO,RecordPayDTO recordPayDTO){
		//下单是不，更新订单为支付失败
		try{
			Map<String,String> map=getRequestData(payParamDTO);
			 return createPayHtml(map);
		}catch(Exception e){
			recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());
			payNotity.updatePayStatus(recordPayDTO, payParamDTO.getPayWay());
			throw e;
		}
	}
	
	
	/**
	 * 异步通知  支付结果验证
	 * 
	 * @param requestParams
	 * @return
	 */
	@Transactional()
	public String payNotifyAlipay(Map<String, String> requestParams) {
		
		RecordPayDTO recordPayDTO = new RecordPayDTO();
		
		NotifyReturnDTO notifyReturnDTO=getNotifyReturn();
		
		try{
			payNotifyAlipayReust(requestParams, recordPayDTO);
		}catch (Exception e) {
			try{
				//验证失败，更新支付失败
				RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO=new RecordPayExceptionMessageDTO();
				superisongCommonRecordPayExceptionMessageDTO.setMethodName(this.getClass().getName()+":payNotifyAlipay");
				superisongCommonRecordPayExceptionMessageDTO.setMethodParams(JsonUtil.classToJson(requestParams));
				superisongCommonRecordPayExceptionMessageDTO.setErrorMessage(ExceptionUtil.getPrintStackTrace(e));
				recordPayExceptionMessageFacade.save(superisongCommonRecordPayExceptionMessageDTO);
			}catch(Exception e1){
			}
			return notifyReturnDTO.getFail();
		}
		
		//通知接口
		try{
			payNotity.updatePayStatus(recordPayDTO, getPayWay());
			return notifyReturnDTO.getSuccess();
		}catch(PayException e){
			//支付通知异常，需要捕捉，并返回失败
			try{
				RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO=new RecordPayExceptionMessageDTO();
				superisongCommonRecordPayExceptionMessageDTO.setMethodName(this.getClass().getName()+":payNotifyAlipay");
				superisongCommonRecordPayExceptionMessageDTO.setMethodParams(JsonUtil.classToJson(requestParams));
				superisongCommonRecordPayExceptionMessageDTO.setErrorMessage(ExceptionUtil.getPrintStackTrace(e));
				recordPayExceptionMessageFacade.save(superisongCommonRecordPayExceptionMessageDTO);
			}catch (Exception e2) {
			}
			return notifyReturnDTO.getFail();
		}catch(BusinessException e){
			//非支付造成的异常，业务方面的异常，例如状态已更新为成功了
			return notifyReturnDTO.getSuccess();
		}catch (Exception e) {
			try{
				RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO=new RecordPayExceptionMessageDTO();
				superisongCommonRecordPayExceptionMessageDTO.setMethodName(this.getClass().getName()+":payNotifyAlipay");
				superisongCommonRecordPayExceptionMessageDTO.setMethodParams(JsonUtil.classToJson(requestParams));
				superisongCommonRecordPayExceptionMessageDTO.setErrorMessage(ExceptionUtil.getPrintStackTrace(e));
				recordPayExceptionMessageFacade.save(superisongCommonRecordPayExceptionMessageDTO);
			}catch (Exception e2) {
			}
			return notifyReturnDTO.getFail();
		}
	}
	
	/**
	 * 同步  支付结果验证
	 * @param requestParams 支付返回结果
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月6日
	 * @records <p>  fushun 2017年1月6日</p>
	 */
	@Transactional()
	public void payResponseValidator(String requestParams) {
		RecordPayDTO recordPayDTO = new RecordPayDTO();
		try{
			payResultAlipayReust(requestParams, recordPayDTO);
		}catch (BusinessException e) {
			try{
				RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO=new RecordPayExceptionMessageDTO();
				superisongCommonRecordPayExceptionMessageDTO.setMethodName(this.getClass().getName()+":payResponseValidator");
				superisongCommonRecordPayExceptionMessageDTO.setMethodParams(JsonUtil.classToJson(requestParams));
				superisongCommonRecordPayExceptionMessageDTO.setErrorMessage(ExceptionUtil.getPrintStackTrace(e));
				recordPayExceptionMessageFacade.save(superisongCommonRecordPayExceptionMessageDTO);
			}catch (Exception e2){}
			payNotity.updatePayStatus(recordPayDTO, getPayWay());
			throw e;
		}catch(Exception e){
			//全部统一返回支付失败
			throw new PayException(e, PayException.Enum.PAY_FAILED_EXCEPTION);
		}

		payNotity.updatePayStatus(recordPayDTO, getPayWay());

	}
	
	
	
	/**
	 * 获取参数Map
	 * @param payParamDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月10日
	 * @records <p>  fushun 2016年9月10日</p>
	 */
	protected abstract Map<String,String> getRequestData(T payParamDTO);
	
	/**
	 * 生成返回支付信息
	 * @param map
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月10日
	 * @records <p>  fushun 2016年9月10日</p>
	 */
	protected abstract String createPayHtml(Map<String,String> map);
	
	/**
	 * 异步通知 验证返回字段
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月10日
	 * @records <p>  fushun 2016年9月10日</p>
	 */
	protected abstract void payNotifyAlipayReust(Map<String, String> requestParams,RecordPayDTO recordPayDTO);

	/**
	 *  获取支付方式
	 */
	protected  abstract  EPayWay getPayWay();
	
	/**
	 * 获取返回通知对象
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月5日
	 * @records <p>  fushun 2017年1月5日</p>
	 */
	protected abstract NotifyReturnDTO getNotifyReturn();
	
	/**
	 * 同步返回验证返回字段
	 * <br>有需要在实现
	 * @param requestParams
	 * @param recordPayDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月17日
	 * @records <p>  fushun 2016年9月17日</p>
	 */
	protected void payResultAlipayReust(String requestParams,RecordPayDTO recordPayDTO){}


}
