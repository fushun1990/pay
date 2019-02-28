package com.fushun.pay.domain.pay.repository;

import com.alibaba.cola.repository.RepositoryI;
import com.fushun.pay.domain.pay.convertor.PayDomainConvertor;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.pay.tunnel.database.PayDBTunnel;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日18时39分
 */
@Repository
public class PayRepository implements RepositoryI {

    @Autowired
    private PayDBTunnel payDBTunnel;

    @Autowired
    private PayDomainConvertor payDomainConvertor;

    public RecordPayDO persist(PayE pay) {
        return payDBTunnel.save(payDomainConvertor.entityToData(pay));
    }

    public Optional<RecordPayDO> findById(RecordPayId recordPayId) {
        return payDBTunnel.findById(recordPayId);
    }

    /**
     * @param payE
     * @param recordPayDO
     * @return void
     * @description 创建支付，已存在，更新支付对象， recordPayDO 托管对象
     * @date 2019年01月20日19时20分
     * @author wangfushun
     * @version 1.0
     */
    public void updateCreatePayInfo(PayE payE, RecordPayDO recordPayDO) {

        payDBTunnel.save(payDomainConvertor.entityToData(payE, recordPayDO));
    }

    /**
     * @param recordPayDO
     * @return void
     * @description 更新托管对象
     * @date 2019年01月22日23时34分
     * @author wangfushun
     * @version 1.0
     */
    public void update(RecordPayDO recordPayDO) {
        payDBTunnel.save(recordPayDO);
    }

    public RecordPayDO findByOutTradeNO(String outTradeNo) {
        RecordPayDO recordPayDO = payDBTunnel.findByOutTradeNO(outTradeNo);
        return recordPayDO;
    }
}
