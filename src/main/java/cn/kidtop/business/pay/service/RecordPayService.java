package cn.kidtop.business.pay.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.kidtop.business.pay.cmp.RecordPayCMP;
import cn.kidtop.business.pay.cmp.RecordPayId;
import cn.kidtop.business.pay.dto.RecordPayDTO;
import cn.kidtop.business.pay.enumeration.ERecordPayNotityStatus;
import cn.kidtop.business.pay.enumeration.ERecordPayStatus;
import cn.kidtop.business.pay.repository.RecordPayRepository;
import cn.kidtop.framework.beans.ConverterUtil;
import cn.kidtop.framework.util.AssertUtils;
import cn.kidtop.framework.util.DateUtil;
import cn.kidtop.framework.util.EnumUtil;

@Service
public class RecordPayService {
	
	@Autowired
	private RecordPayRepository recordPayRepository;
	
	/**
	 * 获取支付信息
	 *  @param outTradeNo 外部订单号
	 *  @return
	 *
	 * @author fushun
	 * @version VS1.3
	 * @creation 2016年4月10日
	 */
	public RecordPayDTO  getRecordPayByOutTradeNoAndPayWay(String outTradeNo)
	{
		RecordPayCMP recordPayCMP= recordPayRepository.findByByOutTradeNo(outTradeNo);
		if(recordPayCMP==null){
			return null;
		}
		return ConverterUtil.convert(recordPayCMP, new RecordPayDTO());
	}
	
	/**
	 * 保存支付信息
	 *  @param recordPayDTO
	 *
	 * @author fushun
	 * @version VS1.3
	 * @creation 2016年4月10日
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void saveRecordPay(RecordPayDTO recordPayDTO)
	{
		RecordPayCMP recordPayCMP = ConverterUtil.convert(recordPayDTO, new RecordPayCMP());
		recordPayRepository.save(recordPayCMP);
	}
	
	/**
	 * 支付订单号相同，更新支付信息，价格，方式，来源，状态，通知状态
	 * @param recordPayDTO
	 * @author fushun
	 * @version dev706
	 * @creation 2017年5月27日
	 * @records <p>  fushun 2017年5月27日</p>
	 */
	public void updateRePayInfo(RecordPayDTO recordPayDTO){
		RecordPayId recordPayId=new RecordPayId(recordPayDTO.getOutTradeNo());
		RecordPayCMP recordPayCMP= recordPayRepository.findById(recordPayId).get();
		recordPayCMP.setPayMoney(recordPayDTO.getPayMoney());
		recordPayCMP.setPayWay(recordPayDTO.getPayWay());
		recordPayCMP.setPayFrom(recordPayDTO.getPayFrom());
		recordPayCMP.setStatus(ERecordPayStatus.created.getCode());
		recordPayCMP.setNotityStatus(ERecordPayNotityStatus.no.getCode());
		recordPayRepository.save(recordPayCMP);
	}
	
	/**
	 * 更新支付信息
	 * <P>如果支付也成功，则直接返回最新对象
	 * <P>如果更新状态为支付失败，则更新支付未失败，并返回最新对象
	 * <P>如果更新状态为支付成功。（支付返回金额，需要等于支付记录中的金额，否则抛出异常“支付金额不匹配”）<br/>则更新支付信息，并返回最新对象
	 *  @param recordPayDTO
	 *  @return
	 *
	 * @author fushun
	 * @version VS1.3
	 * @creation 2016年4月10日
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public RecordPayDTO updatePayStatus(RecordPayDTO recordPayDTO)
	{
		RecordPayCMP recordPayCMP = recordPayRepository.findLockByByOutTradeNo(recordPayDTO.getOutTradeNo());
		if(recordPayCMP ==null){
			throw new PayException(PayException.Enum.PAY_INFO_NO_EXISTS_EXCEPTION);
		}
		
		ERecordPayStatus now=EnumUtil.getEnum(ERecordPayStatus.class, recordPayCMP.getStatus());
		ERecordPayStatus next=EnumUtil.getEnum(ERecordPayStatus.class, recordPayDTO.getStatus());
		
		now.checkStatus(next);
		if(now==ERecordPayStatus.success){
			return ConverterUtil.convert(recordPayCMP, recordPayDTO );
		}
		
		//支付失败，直接更新状态
		if(ERecordPayStatus.failed.getCode().equals(recordPayDTO.getStatus())){
			recordPayCMP.setStatus(recordPayDTO.getStatus());
			 recordPayRepository.save(recordPayCMP);
			 return ConverterUtil.convert(recordPayCMP, recordPayDTO );
		}

		if(recordPayDTO.getPayMoney().compareTo(recordPayCMP.getPayMoney())!=0)
		{
			throw new PayException(null, PayException.Enum.PAY_MONEY_MISMATCHING_EXCEPTION);
		}
		
		recordPayCMP.setPayWay(recordPayDTO.getPayWay());
		recordPayCMP.setPayNo(recordPayDTO.getPayNo());
		recordPayCMP.setPayDate(new Date());
		recordPayCMP.setPayAccount(recordPayDTO.getPayAccount());
		recordPayCMP.setReceiveAccourt(recordPayDTO.getReceiveAccourt());
		recordPayCMP.setReceiveWay(recordPayDTO.getReceiveWay());
		recordPayCMP.setStatus(ERecordPayStatus.success.getCode());
		recordPayCMP.setNotityStatus(ERecordPayNotityStatus.no.getCode());
		recordPayCMP = recordPayRepository.save(recordPayCMP);
		
		return ConverterUtil.convert(recordPayCMP, recordPayDTO);
		
	}
	/**
	 * 更新 系统通知接口
	 * @param recordPayDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年9月17日
	 * @records <p>  fushun 2016年9月17日</p>
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void updateNotityStatus(RecordPayDTO recordPayDTO){
		RecordPayId recordPayId=new RecordPayId(recordPayDTO.getOutTradeNo());
		RecordPayCMP recordPayCMP=recordPayRepository.findById(recordPayId).get();
		AssertUtils.isNull(recordPayCMP);
		recordPayCMP.setNotityStatus(recordPayDTO.getNotityStatus());
		recordPayRepository.save(recordPayCMP);
	}

	/**
	 *  获取在线支付  定时任务的job
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月18日
	 * @records <p>  fushun 2017年1月18日</p>
	 */
	public List<RecordPayDTO> getOnlinePayOrderJob(int pageNo, int pageSize) {
		String sql="select s from RecordPayCMP s where s.updatedAt<=:updatedAt and s.status=:status";
		
		Date date=new Date();
		date=DateUtil.addMinute(date,-2);
		
		Map<String,Object> map=new HashMap<>();
		map.put("status", ERecordPayStatus.created.getCode());
		map.put("updatedAt", date);
		Pageable pageable= PageRequest.of(pageNo, pageSize);
		
		List<RecordPayCMP> recordPayCMPs= recordPayRepository.getList(sql, map, pageable, RecordPayCMP.class);
		return ConverterUtil.convertList(RecordPayCMP.class, RecordPayDTO.class, recordPayCMPs);
	}
	
}
