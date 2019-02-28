package com.fushun.pay.app.command;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.common.exception.ErrorCode;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.app.dto.PaySyncResponseCmd;
import com.fushun.pay.app.dto.clientobject.PaySyncResponseCO;
import com.fushun.pay.app.dto.domainevent.PaySyncResponseExceptionEvent;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.app.validator.extensionpoint.PaySyncResponseValidatorExtPt;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.util.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付同步返回校验
 * @creation 2019年01月23日23时36分
 */
@Command
public class PaySyncResponseCmdExe implements CommandExecutorI<Response, PaySyncResponseCmd> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Override
    public SingleResponse<String> execute(PaySyncResponseCmd cmd) {

        //解析 通知信息
        PaySyncResponseCO paySyncResponseCO = null;
        boolean analysisSyncResponse = true;
        try {
            paySyncResponseCO = extensionExecutor.execute(PaySyncResponseThirdPartyExtPt.class, cmd.getContext(), thirdparty -> thirdparty.responseValidator(cmd.getPaySyncResponseCO()));
        } catch (Exception e) {
            analysisSyncResponse = false;
            logger.error("pay syncResponse  fail, paramMap:[{}]", cmd.getPaySyncResponseCO().getResponseStr(), e);

            PaySyncResponseExceptionEvent paySyncResponseExceptionEvent = new PaySyncResponseExceptionEvent();
            paySyncResponseExceptionEvent.setOutTradeNo(paySyncResponseCO.getOutTradeNo());
            domainEventPublisher.publish(paySyncResponseExceptionEvent);
        }

        //1, validation
        extensionExecutor.executeVoid(PaySyncResponseValidatorExtPt.class, cmd.getContext(), validator -> validator.validate(cmd));

        //2, invoke domain service or directly operate domain to do business logic process
        PayE payE = extensionExecutor.execute(PaySyncResponseConvertorExtPt.class, cmd.getContext(), convertor -> convertor.clientToEntity(cmd.getPaySyncResponseCO(), cmd.getContext()));
        payE.syncResponse();

        if (analysisSyncResponse == false) {
            SingleResponse.buildFailure(ErrorCode.PAY_FAIL.getErrCode(), ErrorCode.PAY_FAIL.getErrCode());
        }
        return SingleResponse.of(ErrorCode.PAY_SUCCESS.getErrDesc());
    }
}
