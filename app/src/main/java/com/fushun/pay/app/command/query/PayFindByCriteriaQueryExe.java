package com.fushun.pay.app.command.query;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.QueryExecutorI;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.fushun.pay.app.common.exception.ErrorCode;
import com.fushun.pay.app.convertor.CreatePayConvertor;
import com.fushun.pay.app.dto.PayFindByCriteriaQry;
import com.fushun.pay.app.dto.clientobject.PayCO;
import com.fushun.pay.infrastructure.pay.tunnel.database.PayDBTunnel;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月19日23时21分
 */
@Command
public class PayFindByCriteriaQueryExe implements QueryExecutorI<SingleResponse<PayCO>, PayFindByCriteriaQry> {
    @Autowired
    private PayDBTunnel payDBTunnel;

    @Autowired
    private CreatePayConvertor payConvertor;

    @Override
    public SingleResponse<PayCO> execute(PayFindByCriteriaQry cmd) {
        RecordPayId recordPayId = new RecordPayId();
        recordPayId.setOutTradeNo(cmd.getOutTradeNo());
        Optional<RecordPayDO> optional = payDBTunnel.findById(recordPayId);
        if(!optional.isPresent()){
            return SingleResponse.buildFailure(ErrorCode.PAY_FAIL.getErrCode(),"支付信息不存在");
        }
        PayCO payCO=payConvertor.dataToClient(optional.get());
        return SingleResponse.of(payCO);
    }
}
