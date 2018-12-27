package cn.kidtop.business.pay.service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import cn.kidtop.business.pay.exception.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.kidtop.business.pay.cmp.RecordRefundCMP;
import cn.kidtop.business.pay.dto.RecordRefundDTO;
import cn.kidtop.business.pay.enumeration.ERefundStatus;
import cn.kidtop.business.pay.repository.RecordRefundRepository;
import cn.kidtop.framework.beans.ConverterUtil;
import cn.kidtop.framework.util.EnumUtil;

/**
 * 退款 服务
 * @author fushun
 *
 * @version V3.0商城
 * @creation 2017年1月7日
 */
@Component
public class RecordRefundService {
	
	@Autowired(required=true)
	private RecordRefundRepository recordRefundRepository;
	
	/**
	 * 查询退款记录
	 * @param recordRefundDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2017年1月7日
	 * @records <p>  fushun 2017年1月7日</p>
	 */
	public RecordRefundDTO query(RecordRefundDTO recordRefundDTO){
		RecordRefundCMP refundParamCMP = recordRefundRepository.findByRefundNoAndPayWay(recordRefundDTO.getRefundNo(), recordRefundDTO.getPayWay());
		if(refundParamCMP==null){
			return null;
		}
		return ConverterUtil.convert(refundParamCMP, recordRefundDTO);
	}
	
	/**
	 * 保存退款记录
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public RecordRefundDTO saveRecordRefund(RecordRefundDTO recordRefundDTO)
	{
		RecordRefundCMP refundParamCMP = ConverterUtil.convert(recordRefundDTO, new RecordRefundCMP());
		recordRefundRepository.save(refundParamCMP);
		return ConverterUtil.convert(refundParamCMP, recordRefundDTO);
	}
	
	/**
	 * 修改 退款状态
	 * @param recordRefundDTO
	 * @return
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年12月11日
	 * @records <p>  fushun 2016年12月11日</p>
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public RecordRefundDTO updateRecordRefund(RecordRefundDTO recordRefundDTO)
	{
		RecordRefundCMP refundParamCMP = recordRefundRepository.findByRefundNoAndPayWay(recordRefundDTO.getRefundNo(), recordRefundDTO.getPayWay());
		
		if(refundParamCMP == null )
		{
			throw new PayException(PayException.Enum.REFUND_INFO_NO_EXISTS_EXCEPTION);
		}
		ERefundStatus now=EnumUtil.getEnum(ERefundStatus.class, refundParamCMP.getStatus());
		ERefundStatus next=EnumUtil.getEnum(ERefundStatus.class, recordRefundDTO.getStatus());
		if(now==null ||next==null){
			throw new PayException(PayException.Enum.REFUND_STATUS_ERROR_EXCEPTION);
		}
		now.checkStatus(next);
		
		refundParamCMP.setStatus(recordRefundDTO.getStatus());
		refundParamCMP.setResult(recordRefundDTO.getResult());
		refundParamCMP.setPayNo(recordRefundDTO.getPayNo());
		
		refundParamCMP = recordRefundRepository.save(refundParamCMP);
		
		return ConverterUtil.convert(refundParamCMP, recordRefundDTO);
	}
	
	/**
	 * 更新 退款通知状态
	 * @param recordRefundDTO
	 * @author fushun
	 * @version V3.0商城
	 * @creation 2016年12月11日
	 * @records <p>  fushun 2016年12月11日</p>
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void updateRecordRefundNoticeStatus(RecordRefundDTO recordRefundDTO) {
		RecordRefundCMP recordRefundCMP= recordRefundRepository.findById(recordRefundDTO.getId()).get();
		recordRefundCMP.setNoticeStatus(recordRefundDTO.getNoticeStatus());
		recordRefundRepository.save(recordRefundCMP);
	}
}
