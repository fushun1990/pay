package cn.kidtop.business.pay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.kidtop.business.pay.cmp.RecordPayExceptionMessageCMP;


/**
 * 支付异常记录
 * @author codeMaker
 *
 * @version 
 * @creation 2016年09月17日
 */
public interface RecordPayExceptionMessageRepository extends CrudRepository<RecordPayExceptionMessageCMP, String>, JpaSpecificationExecutor<String> {
	/**
	 * 获取 支付异常记录 分页列表

	 * @author codeMaker
	 * @version 
	 * @creation 2016年09月17日
	 */
	@Query("select t from RecordPayExceptionMessageCMP t ")
	Page<RecordPayExceptionMessageCMP> getPage(Pageable pageable);

	/**
	 * 获取单个对象 支付异常记录
	 * @param id
	 * @return
	 * @author fushun
	 * @version 
	 * @creation 2016年09月17日
	 */
	@Query("select t from RecordPayExceptionMessageCMP t where t.id=:id ")
	RecordPayExceptionMessageCMP getById(@Param("id")String id);
}