package cn.kidtop.business.pay.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tencent.common.AppCConfigure;
import com.tencent.common.Configure;
import com.tencent.common.GZHConfigure;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;

import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.dto.RecordPayExceptionMessageDTO;
import cn.kidtop.business.pay.dto.TradeQueryRequestDTO;
import cn.kidtop.business.pay.enumeration.EPayFrom;
import cn.kidtop.business.pay.enumeration.EPayWay;
import cn.kidtop.business.pay.enumeration.ERecordPayStatus;
import cn.kidtop.business.pay.facade.notity.PayNotity;
import cn.kidtop.business.pay.facade.pay.impl.alipay.AlipayAppTradeQueryFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeiXinPayQueryFacade;
import cn.kidtop.business.pay.facade.pay.impl.winxinpay.WeiXinUnifiedOrderFacade;
import cn.kidtop.business.pay.service.RecordPayService;
import cn.kidtop.framework.exception.BusinessException;
import cn.kidtop.framework.util.EnumUtil;
import cn.kidtop.framework.util.ExceptionUtil;
import cn.kidtop.framework.util.JsonUtil;

/**
 * job 定时任务 获取支付状态，更新数据
 * @author fushun
 *
 * @version dev706
 * @creation 2017年6月1日
 */
@Component
public class JobPayFacade {

	private Logger logger = LoggerFactory.getLogger(JobPayFacade.class);

	@Autowired
	private RecordPayService recordPayService;
	
	@Autowired
	private AlipayAppTradeQueryFacade alipayAppTradeQueryFacade;
	@Autowired
	private RecordPayExceptionMessageFacade superisongCommonRecordPayExceptionMessageFacade;
	@Autowired
	private PayNotity payNotity;
	@Autowired
	private WeiXinPayQueryFacade weiXinPayQueryFacade;
	
	/**
	 * 获取在线支付  定时任务的job
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年12月20日
	 * @records <p>  fushun 2016年12月20日</p>
	 */
	@Transactional
	public List<RecordPayDTO> getOnlinePayOrderJob(int pageNo,int pageSize){
		return recordPayService.getOnlinePayOrderJob(pageNo,pageSize);
	}
	
	/**
	 * 在线支付定时任务 执行程序
	 * @param recordPayDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年12月20日
	 * @records <p>  fushun 2016年12月20日</p>
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void getOnlinePayOrderJobExecute(RecordPayDTO recordPayDTO){
		TradeQueryRequestDTO tradeQueryRequestDTO=new TradeQueryRequestDTO();
		tradeQueryRequestDTO.setOutTradeNo(recordPayDTO.getOutTradeNo());
		tradeQueryRequestDTO.setePayFrom(EnumUtil.getEnum(EPayFrom.class,recordPayDTO.getPayFrom()));
		EPayWay ePayWay=EnumUtil.getEnum(EPayWay.class, recordPayDTO.getPayWay());
		if(ePayWay==null){
			return ;
		}
		try{
			switch (ePayWay) {
				case PAY_WAY_ALIPAY:
					checkAppAlipayTradeQuery(tradeQueryRequestDTO,ePayWay);
					break;
				case PAY_WAY_WEIXINPAY:
					checkWeiWinJsTradeQuery(tradeQueryRequestDTO,ePayWay,GZHConfigure.initMethod());
					break;
				case PAY_WAY_APPC_WEIXINPAY:
					checkWeiWinJsTradeQuery(tradeQueryRequestDTO,ePayWay,AppCConfigure.initMethod());
					break;
				default:
					break;
			}
		}catch(Exception e){
			logger.debug(ExceptionUtil.getPrintStackTrace(e));
		};
	}
	
	/**
	 * 支付宝 检测支付状态，并更新通知
	 * @param tradeQueryRequestDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月18日
	 * @records <p>  fushun 2016年9月18日</p>
	 */
	private void checkAppAlipayTradeQuery(TradeQueryRequestDTO tradeQueryRequestDTO,EPayWay payWay){
		RecordPayDTO recordPayDTO=null;
		try{
			
			 recordPayDTO= alipayAppTradeQueryFacade.tradeQuery(tradeQueryRequestDTO);
		}catch (BusinessException e) {
			try{
				RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO=new RecordPayExceptionMessageDTO();
				superisongCommonRecordPayExceptionMessageDTO.setMethodName(this.getClass().getName()+":checkAppAlipayTradeQuery");
				List<Object> obj=new ArrayList<Object>();
				obj.add(tradeQueryRequestDTO);
				obj.add(payWay);
				superisongCommonRecordPayExceptionMessageDTO.setMethodParams(JsonUtil.classToJson(obj));
				superisongCommonRecordPayExceptionMessageDTO.setErrorMessage(ExceptionUtil.getPrintStackTrace(e));
				superisongCommonRecordPayExceptionMessageFacade.save(superisongCommonRecordPayExceptionMessageDTO);
			}catch (Exception e2) {
			}
			return ;
		}
			
		payNotity.updatePayStatus(recordPayDTO, payWay);

	}
	
	
	/**
	 * 微信支付  查询支付状态 并更新支付信息
	 * @param tradeQueryRequestDTO
	 * @param payWay
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月20日
	 * @records <p>  fushun 2017年1月20日</p>
	 */
	private void checkWeiWinJsTradeQuery(TradeQueryRequestDTO tradeQueryRequestDTO,EPayWay payWay,Configure configure){
		
		RecordPayDTO recordPayDTO=null;
		OrderQueryResData orderQueryResData=null;
		recordPayDTO=new RecordPayDTO();
		recordPayDTO.setOutTradeNo(tradeQueryRequestDTO.getOutTradeNo());
		recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());
		try{
			 orderQueryResData=weiXinPayQueryFacade.getOrderQuery(tradeQueryRequestDTO.getOutTradeNo(),configure);
			 	
			 recordPayDTO.setPayNo(orderQueryResData.getTransaction_id());
			 recordPayDTO.setOutTradeNo(orderQueryResData.getOut_trade_no());
			 recordPayDTO.setStatus(ERecordPayStatus.success.getCode());
			 recordPayDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(orderQueryResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
		}catch (BusinessException e) {
			try{
				RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO=new RecordPayExceptionMessageDTO();
				superisongCommonRecordPayExceptionMessageDTO.setMethodName(this.getClass().getName()+":checkWeiWinJsTradeQuery");
				List<Object> obj=new ArrayList<Object>();
				obj.add(tradeQueryRequestDTO);
				obj.add(payWay);
				superisongCommonRecordPayExceptionMessageDTO.setMethodParams(JsonUtil.classToJson(obj));
				superisongCommonRecordPayExceptionMessageDTO.setErrorMessage(ExceptionUtil.getPrintStackTrace(e));
				superisongCommonRecordPayExceptionMessageFacade.save(superisongCommonRecordPayExceptionMessageDTO);
			}catch (Exception e2) {
			}
		}
		payNotity.updatePayStatus(recordPayDTO, payWay);
	}
}
