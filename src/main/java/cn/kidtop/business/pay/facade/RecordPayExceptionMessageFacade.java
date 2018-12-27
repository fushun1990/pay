package cn.kidtop.business.pay.facade;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.kidtop.business.pay.dto.RecordPayExceptionMessageDTO;
import cn.kidtop.business.pay.service.RecordPayExceptionMessageService;

/**
 * 支付异常记录
 * @author codeMaker
 *
 * @version 
 * @creation 2016年09月17日
 */
@Service(value="recordPayExceptionMessageFacade")
public class RecordPayExceptionMessageFacade {

	@Autowired
	private RecordPayExceptionMessageService superisongCommonRecordPayExceptionMessageService;
	
	/**
	 * 保存
	 * @author codeMaker
	 *
	 * @version
	 * @creation 2016年09月17日
	 */
	 @Transactional(value=TxType.REQUIRES_NEW)
	public void save(RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO){
		superisongCommonRecordPayExceptionMessageService.save(superisongCommonRecordPayExceptionMessageDTO);
	}
	
	/**
	 * 修改
	 * @author codeMaker
	 *
	 * @version
	 * @creation 2016年09月17日
	 */
	 @Transactional
	public void update(RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO){
		superisongCommonRecordPayExceptionMessageService.update(superisongCommonRecordPayExceptionMessageDTO);
	}
	
	/**
	 * 获取单个  支付异常记录
	 * @author codeMaker
	 * @version 
	 * @creation 2016年09月17日
	 */
	public RecordPayExceptionMessageDTO getById(RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO){
		return superisongCommonRecordPayExceptionMessageService.getById(superisongCommonRecordPayExceptionMessageDTO);
	};
	
	/**
	 * 获取分页  支付异常记录
	 * @author codeMaker
	 * @version 
	 * @creation 2016年09月17日
	 */	
	public Page<RecordPayExceptionMessageDTO> getPage(RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO,int pageNo,int pageSize){
		return superisongCommonRecordPayExceptionMessageService.getPage(superisongCommonRecordPayExceptionMessageDTO, pageNo, pageSize);
	};
}
