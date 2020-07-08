package com.fushun.pay.app.command;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.fushun.pay.app.common.exception.ErrorCode;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.app.dto.CreatePayCmd;
import com.fushun.pay.dto.clientobject.createpay.EStatus;
import com.fushun.pay.dto.clientobject.createpay.response.CreatedPayVO;
import com.fushun.pay.app.dto.domainevent.CreatedPayExceptionEvent;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.app.thirdparty.extensionpoint.CreatePayThirdPartyExtPt;
import com.fushun.pay.app.validator.extensionpoint.CreatePayValidatorExtPt;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.infrastructure.common.util.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时08分
 */
@Component
public class PayCmdExe {


    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    public SingleResponse<CreatedPayVO> execute(CreatePayCmd cmd) {
        //1, validation
        extensionExecutor.executeVoid(CreatePayValidatorExtPt.class, cmd.getBizScenario(), extension -> extension.validate(cmd));

        //2, invoke domain service or directly operate domain to do business logic process
        PayE payE = extensionExecutor.execute(CreatePayConvertorExtPt.class, cmd.getBizScenario(), convertor -> convertor.clientToEntity(cmd.getPayCO(), cmd.getBizScenario()));
        payE.pay();

        //获取支付信息
        CreatedPayVO payRequestBody = extensionExecutor.execute(CreatePayThirdPartyExtPt.class, cmd.getBizScenario(), thirdparty -> thirdparty.created(cmd.getPayCO()));

        if (payRequestBody.getStatus() == EStatus.FAIL) {
            CreatedPayExceptionEvent createdPayExceptionEvent = new CreatedPayExceptionEvent();
            createdPayExceptionEvent.setOutTradeNo(payE.getOutTradeNo());
            createdPayExceptionEvent.setOrderPayNo(payE.getTradeNo());
            domainEventPublisher.publish(createdPayExceptionEvent);

            payE.setStatus(ERecordPayStatus.FAILED);
            payE.payFail();
            return SingleResponse.buildFailure(ErrorCode.CREATED_PAY_BODY.getErrCode(), ErrorCode.CREATED_PAY_BODY.getErrDesc());
        }

        ;
        //3, response
        return SingleResponse.of(payRequestBody);
    }
}
