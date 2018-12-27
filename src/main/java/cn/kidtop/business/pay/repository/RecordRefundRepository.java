package cn.kidtop.business.pay.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cn.kidtop.business.pay.cmp.RecordRefundCMP;

public  interface RecordRefundRepository extends CrudRepository<RecordRefundCMP, BigDecimal>, JpaSpecificationExecutor<RecordRefundCMP> {

	/**
	 * 获取退款数据
	 * @param refundNo 退款批次号
	 * @param payWay 支付方式
	 * @return
	 * @author fushun
	 * @version VS1.3
	 * @creation 2016年4月10日
	 */
	@Query("select s from RecordRefundCMP s where s.refundNo = ?1 and s.payWay = ?2")
	public RecordRefundCMP findByRefundNoAndPayWay(String refundNo, String payWay);
}
