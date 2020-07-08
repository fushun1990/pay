package com.fushun.pay.domain.pay.entity;


import com.alibaba.cola.domain.Entity;
import com.alibaba.cola.domain.EntityObject;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.util.util.BeanUtils;
import com.fushun.pay.app.dto.domainevent.CreatedPayEvent;
import com.fushun.pay.app.dto.domainevent.PaySuccessNotityEvent;
import com.fushun.pay.app.dto.enumeration.EPayFrom;
import com.fushun.pay.app.dto.enumeration.EPayWay;
import com.fushun.pay.app.dto.enumeration.ERecordPayNotityStatus;
import com.fushun.pay.app.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.domain.pay.repository.PayRepository;
import com.fushun.pay.infrastructure.common.util.DomainEventPublisher;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayId;
import lombok.Data;

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

    private PayRepository payRepository= SpringContextUtil.getBean(PayRepository.class);

    private DomainEventPublisher domainEventPublisher=SpringContextUtil.getBean(DomainEventPublisher.class);;

    /**
     * @param
     * @return void
     * @description 保存
     * @date 2019年01月20日19时05分
     * @author wangfushun
     * @version 1.0
     */
    public void pay() {

        RecordPayId recordPayId = new RecordPayId(this.outTradeNo);
        Optional<RecordPayDO> optional = payRepository.findById(recordPayId);
        RecordPayDO recordPayDO = null;
        if (optional.isPresent() == false) {
            recordPayDO = payRepository.persist(this);

            CreatedPayEvent createdPayEvent = new CreatedPayEvent();
            createdPayEvent.setOutTradeNo(recordPayDO.getOutTradeNo());
            createdPayEvent.setOrderPayNo(recordPayDO.getPayNo());
            domainEventPublisher.publish(createdPayEvent);
            return;
        }
        recordPayDO = optional.get();

        ERecordPayStatus eRecordPayStatus = recordPayDO.getStatus();
        if (eRecordPayStatus != ERecordPayStatus.SUCCESS) {
            payRepository.updateCreatePayInfo(this, recordPayDO);
            return;
        }

        //支付成功，已通知
        ERecordPayNotityStatus eRecordPayNotityStatus = recordPayDO.getNotityStatus();
        if (eRecordPayStatus == ERecordPayStatus.SUCCESS && eRecordPayNotityStatus == ERecordPayNotityStatus.YES) {
            throw new PayException(PayException.PayExceptionEnum.PAY_SUCCESS);
        }

        //支付成功，为通知成功
        if (eRecordPayStatus == ERecordPayStatus.SUCCESS && eRecordPayNotityStatus == ERecordPayNotityStatus.NO) {
            PaySuccessNotityEvent paySuccessNotityEvent = new PaySuccessNotityEvent();
            paySuccessNotityEvent.setOutTradeNo(recordPayDO.getOutTradeNo());
            paySuccessNotityEvent.setOrderPayNo(recordPayDO.getPayNo());
            domainEventPublisher.publish(paySuccessNotityEvent);
            return;
        }

        throw new PayException(PayException.PayExceptionEnum.PAY_FAILED);

    }

    /**
     * @return a
     * @description 支付异步通知更新
     * @date 2019年01月24日01时27分
     * @author wangfushun
     * @version 1.0
     */
    public void payNotify() {
        RecordPayId recordPayId = new RecordPayId(this.getOutTradeNo());

        Optional<RecordPayDO> optional=payRepository.findById(recordPayId);

        if (!optional.isPresent()) {
            throw new PayException(null, PayException.PayExceptionEnum.PAY_INFO_NO_EXISTS);
        }
        RecordPayDO recordPayDO = optional.get();

        ERecordPayStatus now =recordPayDO.getStatus();
        ERecordPayStatus next =  this.getStatus();

        if (now == ERecordPayStatus.SUCCESS) {
            logger.info("already pay,outTradeNo:[{}]", this.getOutTradeNo());
            return;
        }

        //支付失败，直接更新状态
        if (ERecordPayStatus.FAILED.getCode().equals(this.getStatus())) {
            recordPayDO.setStatus(this.getStatus());
            payRepository.update(recordPayDO);
            logger.info("pay failed,outTradeNo:[{}]", this.outTradeNo);
            return;
        }

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
        ERecordPayStatus now = recordPayDO.getStatus();
        ERecordPayStatus next = this.getStatus();

        if (now == ERecordPayStatus.SUCCESS) {
            logger.info("paid is pay,outTradeNo:[{}]", this.getOutTradeNo());
            return;
        }

        //支付失败，直接更新状态
        if (ERecordPayStatus.FAILED.getCode().equals(this.getStatus())) {
            recordPayDO.setStatus(this.getStatus());
            payRepository.update(recordPayDO);
            logger.info("pay failed,outTradeNo:[{}]", this.outTradeNo);
            return;
        }

        if (BeanUtils.isNull(this.payMoney) || this.payMoney.compareTo(recordPayDO.getPayMoney()) != 0) {
            logger.info("pay failed,outTradeNo:[{}]", this.outTradeNo);
            throw new PayException(null, PayException.PayExceptionEnum.PAY_MONEY_MISMATCHING);
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
            throw new PayException(null, PayException.PayExceptionEnum.PAY_INFO_NO_EXISTS);
        }
        RecordPayDO recordPayDO = optional.get();


        ERecordPayStatus now = recordPayDO.getStatus();
        ERecordPayStatus next = this.getStatus();

        if (now == ERecordPayStatus.SUCCESS) {
            logger.info("paid is pay,outTradeNo:[{}]", this.getOutTradeNo());
            return;
        }

        //支付失败，直接更新状态
        if (ERecordPayStatus.FAILED.getCode().equals(this.getStatus())) {
            recordPayDO.setStatus(this.getStatus());
            payRepository.update(recordPayDO);
            logger.info("pay failed,outTradeNo:[{}]", this.outTradeNo);
            return;
        }
    }
}
