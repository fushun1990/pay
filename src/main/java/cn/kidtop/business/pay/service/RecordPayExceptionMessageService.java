package cn.kidtop.business.pay.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.kidtop.business.pay.cmp.RecordPayExceptionMessageCMP;
import cn.kidtop.business.pay.dto.RecordPayExceptionMessageDTO;
import cn.kidtop.business.pay.repository.RecordPayExceptionMessageRepository;
import cn.kidtop.framework.beans.ConverterUtil;

/**
 * 支付异常记录
 * @author codeMaker
 *
 * @version 
 * @creation 2016年09月17日
 */
@Service("SuperisongCommonRecordPayExceptionMessageService")
public class RecordPayExceptionMessageService {
	@Autowired
	private RecordPayExceptionMessageRepository recordPayExceptionMessageRepository;


	/**
	 * 保存
	 * @author codeMaker
	 *
	 * @version
	 * @creation 2016年09月17日
	 */
	public void save(RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO) {
		RecordPayExceptionMessageCMP recordPayExceptionMessageCMP = ConverterUtil.convert(superisongCommonRecordPayExceptionMessageDTO, new RecordPayExceptionMessageCMP());
		recordPayExceptionMessageRepository.save(recordPayExceptionMessageCMP);
		ConverterUtil.convert(recordPayExceptionMessageCMP, superisongCommonRecordPayExceptionMessageDTO);
		return ;
	}

	/**
	 * 修改
	 * @author codeMaker
	 *
	 * @version
	 * @creation 2016年09月17日
	 */
	public void update(RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO) {
		RecordPayExceptionMessageCMP recordPayExceptionMessageCMP= recordPayExceptionMessageRepository.getById(superisongCommonRecordPayExceptionMessageDTO.getId());
		recordPayExceptionMessageCMP = ConverterUtil.convertJPAEntity(superisongCommonRecordPayExceptionMessageDTO,recordPayExceptionMessageCMP);
		recordPayExceptionMessageRepository.save(recordPayExceptionMessageCMP);
		ConverterUtil.convert(recordPayExceptionMessageCMP, superisongCommonRecordPayExceptionMessageDTO);
		return ;
	}
	
	/**
	 * 获取单个  支付异常记录
	 * @author codeMaker
	 * @version 
	 * @creation 2016年09月17日
	 */
	public RecordPayExceptionMessageDTO getById(RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO){
		RecordPayExceptionMessageCMP recordPayExceptionMessageCMP=recordPayExceptionMessageRepository.getById(superisongCommonRecordPayExceptionMessageDTO.getId());
		ConverterUtil.convert(recordPayExceptionMessageCMP, superisongCommonRecordPayExceptionMessageDTO);
		return superisongCommonRecordPayExceptionMessageDTO;
	};
	
	/**
	 * 获取分页  支付异常记录
	 * @author codeMaker
	 * @version 
	 * @creation 2016年09月17日
	 */
	public Page<RecordPayExceptionMessageDTO> getPage(RecordPayExceptionMessageDTO superisongCommonRecordPayExceptionMessageDTO,int pageNo,int pageSize){
		
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		
		Page<RecordPayExceptionMessageCMP> page=recordPayExceptionMessageRepository.getPage(pageable);
		List<RecordPayExceptionMessageDTO> superisongCommonRecordPayExceptionMessageDTOs=ConverterUtil.convertList(RecordPayExceptionMessageCMP.class, RecordPayExceptionMessageDTO.class, page.getContent());
		Page<RecordPayExceptionMessageDTO> page2=new PageImpl<RecordPayExceptionMessageDTO>(superisongCommonRecordPayExceptionMessageDTOs, pageable, page.getTotalElements());
		return page2;
	};
}
