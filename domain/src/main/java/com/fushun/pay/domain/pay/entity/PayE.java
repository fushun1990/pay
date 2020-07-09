package com.fushun.pay.domain.pay.entity;


import com.alibaba.cola.domain.Entity;
import com.alibaba.cola.domain.EntityObject;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.util.util.BeanUtils;
import com.fushun.framework.util.util.ExceptionUtils;
import com.fushun.pay.client.dto.domainevent.CreatedPayEvent;
import com.fushun.pay.client.dto.domainevent.PaySuccessNotityEvent;
import com.fushun.pay.client.dto.enumeration.ERecordPayNotityStatus;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.domain.pay.repository.PayRepository;
import com.fushun.pay.dto.clientobject.createpay.enumeration.ECreatePayStatus;
import com.fushun.pay.dto.enumeration.EPayFrom;
import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.infrastructure.common.util.DomainEventPublisher;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayId;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日18时30分
 */
@Entity
@Data
public class PayE extends EntityObject {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 支付方式
     */
    private EPayWay payWay; //支付宝，网银，银联等
    /**
     * 支付源
     */
    private EPayFrom payFrom; //web端，App端
    /**
     * 通知连接
     */
    private String notifyUrl;
    /**
     * 内部 支付单号
     */
    private String tradeNo;

    /**
     * 支付单号
     */
    private String outTradeNo;

    /**
     * 外部系统 支付单号
     */
    private String payNo;

    /**
     * 支付金额
     * 保留两位小数
     */
    private BigDecimal payMoney;

    /**
     * 支付状态
     */
    private ERecordPayStatus status;

    /**
     * 支付账号
     */
    private String payAccount;

    /**
     * 收款账号
     */
    private String receiveAccourt;

    /**
     * 收款方式
     */
    private EPayWay receiveWay;

    /**
     * 系统通知 状态，1：已通知,2：未通知
     */
    private ERecordPayNotityStatus notityStatus;

    /**
     * 已退金额
     */
    private BigDecimal refundAmount;

    private PayRepository payRepository= SpringContextUtil.getBean(PayRepository.class);

    private DomainEventPublisher domainEventPublisher=SpringContextUtil.getBean(DomainEventPublisher.class);;

    /**
     *
     * 没有创建过 创建支付
     * 已创建过
     *      支付没有成功
     *      支付成功
     *            已通知
     *            未通知
     *
     * @return void
     * @description 保存
     * @date 2019年01月20日19时05分
     * @author wangfushun
     * @version 1.0
     */
    public ECreatePayStatus pay() {

        RecordPayId recordPayId = new RecordPayId(this.outTradeNo);
        Optional<RecordPayDO> optional = payRepository.findById(recordPayId);
        RecordPayDO recordPayDO = null;

        //没有创建过
        if (!optional.isPresent()) {
            this.setRefundAmount(BigDecimal.ZERO);
            this.setStatus(ERecordPayStatus.CREATED);
            this.setNotityStatus(ERecordPayNotityStatus.NO);
            recordPayDO = payRepository.persist(this);
            CreatedPayEvent createdPayEvent = new CreatedPayEvent();
            createdPayEvent.setOutTradeNo(recordPayDO.getOutTradeNo());
            createdPayEvent.setOrderPayNo(recordPayDO.getPayNo());
            domainEventPublisher.publish(createdPayEvent);
            return ECreatePayStatus.SUCCESS;
        }

        //已创建支付，
        recordPayDO = optional.get();

        ERecordPayStatus eRecordPayStatus = recordPayDO.getStatus();
        //没有支付成功的
        if (eRecordPayStatus != ERecordPayStatus.SUCCESS) {
            this.setRefundAmount(BigDecimal.ZERO);
            this.setStatus(ERecordPayStatus.CREATED);
            this.setNotityStatus(ERecordPayNotityStatus.NO);
            payRepository.updateCreatePayInfo(this, recordPayDO);
            return ECreatePayStatus.SUCCESS;
        }

        //支付成功，已通知
        ERecordPayNotityStatus eRecordPayNotityStatus = recordPayDO.getNotityStatus();
        if (eRecordPayStatus == ERecordPayStatus.SUCCESS && eRecordPayNotityStatus == ERecordPayNotityStatus.YES) {
            return ECreatePayStatus.HAS_PAY_SUCCESS;
        }

        //支付成功，未通知
        if (eRecordPayStatus == ERecordPayStatus.SUCCESS && eRecordPayNotityStatus == ERecordPayNotityStatus.NO) {
            PaySuccessNotityEvent paySuccessNotityEvent = new PaySuccessNotityEvent();
            paySuccessNotityEvent.setOutTradeNo(recordPayDO.getOutTradeNo());
            paySuccessNotityEvent.setOrderPayNo(recordPayDO.getPayNo());
            domainEventPublisher.publish(paySuccessNotityEvent);

            //更新通知成功
            recordPayDO.setNotityStatus(ERecordPayNotityStatus.YES);
            payRepository.update(recordPayDO);
            return ECreatePayStatus.HAS_PAY_SUCCESS;
        }

        return ECreatePayStatus.FAIL;

    }

    /**
     * @return a
     * @description 支付异步通知更新
     * @date 2019年01月24日01时27分
     * @author wangfushun
     * @version 1.0
     */
    @Transactional(rollbackOn = Exception.class)
    public void payNotify() {
        RecordPayId recordPayId = new RecordPayId(this.getOutTradeNo());

        RecordPayDO recordPayDO=payRepository.findLockById(recordPayId);

        if (BeanUtils.isNull(recordPayDO)) {
            throw new PayException(PayException.PayExceptionEnum.PAY_INFO_NO_EXISTS);
        }

        //已支付成功，直接更新
        if (recordPayDO.getStatus() == ERecordPayStatus.SUCCESS) {
            logger.info("already pay,outTradeNo:[{}]", this.getOutTradeNo());
            return;
        }

        //支付失败，直接更新状态
        if (ERecordPayStatus.FAILED==this.getStatus()) {
            recordPayDO.setStatus(this.getStatus());
            payRepository.update(recordPayDO);
            logger.info("pay failed,outTradeNo:[{}]", this.outTradeNo);
            return;
        }

        //第三方支付返回金额不等于 发出支付金额
        if (BeanUtils.isNull(this.payMoney) || this.payMoney.compareTo(recordPayDO.getPayMoney()) != 0) {
            logger.info("pay failed,outTradeNo:[{}]", this.outTradeNo);
            throw new PayException(null, PayException.PayExceptionEnum.PAY_MONEY_MISMATCHING);
        }

        recordPayDO.setPayNo(this.payNo);
        recordPayDO.setStatus(this.status);
        recordPayDO.setPayDate(new Date());
        recordPayDO.setPayAccount(this.payAccount);
        recordPayDO.setReceiveAccourt(this.receiveAccourt);
        recordPayDO.setReceiveWay(this.receiveWay.getCode());
        recordPayDO.setNotityStatus(ERecordPayNotityStatus.NO);
        payRepository.update(recordPayDO);
        logger.info("pay notify,outTradeNo:[{}],status:[{}]", this.outTradeNo, this.status);
    }

    /**
     * @return a
     * @description 同支付结果更新
     * @date 2019年01月24日01时27分
     * @author wangfushun
     * @version 1.0
     */
    public void syncResponse() {
        RecordPayId recordPayId = new RecordPayId(this.getOutTradeNo());
        Optional<RecordPayDO> optional=payRepository.findById(recordPayId);

        if (!optional.isPresent()) {
            throw new PayException(null, PayException.PayExceptionEnum.PAY_INFO_NO_EXISTS);
        }
        RecordPayDO recordPayDO = optional.get();

        if (recordPayDO.getStatus() == ERecordPayStatus.SUCCESS) {
            throw new PayException(null, PayException.PayExceptionEnum.PAY_SUCCESS);
        }

        //支付失败，直接更新状态
        if (ERecordPayStatus.FAILED==this.getStatus()) {
            recordPayDO.setStatus(this.getStatus());
            payRepository.update(recordPayDO);
            logger.info("pay failed,outTradeNo:[{}]", this.outTradeNo);
            return;
        }

        //判断第三方支付返回的金额，不等于支付时的金额
        if (BeanUtils.isNull(this.payMoney) || this.payMoney.compareTo(recordPayDO.getPayMoney()) != 0) {
            ExceptionUtils.rethrow(new PayException(null, PayException.PayExceptionEnum.PAY_MONEY_MISMATCHING),
                    logger,"pay failed,outTradeNo:[{}]", this.outTradeNo);
        }

        recordPayDO.setPayNo(this.payNo);
        recordPayDO.setStatus(this.status);
        recordPayDO.setPayDate(new Date());
//        recordPayDO.setPayAccount(this.payAccount);
//        recordPayDO.setReceiveAccourt(this.receiveAccourt);
        recordPayDO.setReceiveWay(this.receiveWay.getCode());
        recordPayDO.setNotityStatus(ERecordPayNotityStatus.NO);
        payRepository.update(recordPayDO);
    }

    /**
     * @return a
     * @description 支付失败
     * @date 2019年01月29日21时05分
     * @author wangfushun
     * @version 1.0
     */
    public void payFail() {
        RecordPayId recordPayId = new RecordPayId(this.getOutTradeNo());

        Optional<RecordPayDO> optional=payRepository.findById(recordPayId);

        if (!optional.isPresent()) {
            logger.warn("pay is not exists,outTradeNo:[{}]", this.getOutTradeNo());
            throw new PayException(PayException.PayExceptionEnum.PAY_INFO_NO_EXISTS);
        }
        RecordPayDO recordPayDO = optional.get();

        //已支付成功，不能更新为失败
        if (recordPayDO.getStatus() == ERecordPayStatus.SUCCESS) {
            logger.warn("paid is pay,outTradeNo:[{}]", this.getOutTradeNo());
            throw new PayException(PayException.PayExceptionEnum.PAY_SUCCESS);
        }

        //支付失败，直接更新状态
        if (ERecordPayStatus.FAILED==this.getStatus()) {
            recordPayDO.setStatus(this.getStatus());
            payRepository.update(recordPayDO);
            if(logger.isDebugEnabled()){
                logger.debug("pay failed,outTradeNo:[{}]", this.outTradeNo);
            }
            return ;
        }
    }
}
