package cn.kidtop.business.pay.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.kidtop.business.pay.cmp.RecordPayCMP;
import cn.kidtop.business.pay.cmp.RecordPayId;
import cn.kidtop.framework.jpa.CustomerRepository;

public  interface RecordPayRepository extends CustomerRepository<RecordPayCMP, RecordPayId> {

	/**
	 * 获取支付信息
	 * <P>加锁
	 * @param outTradeNo
	 * @return
	 * @date: 2017-09-20 11:48:59
	 * @author:wangfushun
	 * @version 1.0
	 */
	@Query("select s from RecordPayCMP s where s.outTradeNo =:outTradeNo")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public RecordPayCMP findLockByByOutTradeNo(@Param("outTradeNo")String outTradeNo);
	
	/**
	 * 获取支付信息
	 * @param outTradeNo
	 * @return
	 * @date: 2017-09-20 11:48:45
	 * @author:wangfushun
	 * @version 1.0
	 */
	@Query("select s from RecordPayCMP s where s.outTradeNo =:outTradeNo")
	public RecordPayCMP findByByOutTradeNo(@Param("outTradeNo")String outTradeNo);
}
