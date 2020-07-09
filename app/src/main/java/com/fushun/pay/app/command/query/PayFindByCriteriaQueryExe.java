package com.fushun.pay.app.command.query;

import com.alibaba.cola.dto.SingleResponse;
import com.fushun.pay.app.common.exception.ErrorCode;
import com.fushun.pay.app.convertor.CreatePayConvertor;
import com.fushun.pay.client.dto.PayFindByCriteriaQry;
import com.fushun.pay.dto.clientobject.PayDTO;
import com.fushun.pay.infrastructure.pay.tunnel.database.PayDBTunnel;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月19日23时21分
 */
@Component
public class PayFindByCriteriaQueryExe {
    @Autowired
    private PayDBTunnel payDBTunnel;

    @Autowired
    private CreatePayConvertor payConvertor;

    public SingleResponse<PayDTO> execute(PayFindByCriteriaQry cmd) {
        RecordPayId recordPayId = new RecordPayId();
        recordPayId.setOutTradeNo(cmd.getOutTradeNo());
        Optional<RecordPayDO> optional = payDBTunnel.findById(recordPayId);
        if(!optional.isPresent()){
            return SingleResponse.buildFailure(ErrorCode.PAY_FAIL.getErrCode(),"支付信息不存在");
        }
        PayDTO payDTO =payConvertor.dataToClient(optional.get());
        return SingleResponse.of(payDTO);
    }
}
