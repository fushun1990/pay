package com.fushun.pay.app.command.query;

import com.alibaba.cola.command.QueryExecutorI;
import com.alibaba.cola.dto.MultiResponse;
import com.fushun.pay.app.convertor.CreatePayConvertor;
import com.fushun.pay.app.dto.PayFindByCriteriaQry;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.infrastructure.pay.tunnel.database.PayDBTunnel;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月19日23时21分
 */
public class PayFindByCriteriaQueryExe implements QueryExecutorI<MultiResponse<PayCO>, PayFindByCriteriaQry> {
    @Autowired
    private PayDBTunnel payDBTunnel;

    @Autowired
    private CreatePayConvertor payConvertor;

    @Override
    public MultiResponse<PayCO> execute(PayFindByCriteriaQry cmd) {
        RecordPayId recordPayId = new RecordPayId();
        recordPayId.setOutTradeNo("123");
        RecordPayDO recordPayDO = payDBTunnel.findById(recordPayId).get();
        List<PayCO> customerCos = new ArrayList<>();
        customerCos.add(payConvertor.dataToClient(recordPayDO));
        return MultiResponse.of(customerCos, customerCos.size());
    }
}
