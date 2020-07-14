package com.fushun.pay.app.command;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.fushun.framework.util.util.StringUtils;
import com.fushun.pay.app.common.exception.ErrorCode;
import com.fushun.pay.app.convertor.extensionpoint.CreatePayConvertorExtPt;
import com.fushun.pay.app.thirdparty.extensionpoint.CreatePayThirdPartyExtPt;
import com.fushun.pay.app.validator.extensionpoint.CreatePayValidatorExtPt;
import com.fushun.pay.client.config.PayConfig;
import com.fushun.pay.client.dto.CreatePayCmd;
import com.fushun.pay.client.dto.domainevent.CreatedPayExceptionEvent;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.dto.clientobject.createpay.enumeration.ECreatePayStatus;
import com.fushun.pay.dto.clientobject.createpay.response.CreatedPayVO;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.infrastructure.common.util.DomainEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时08分
 */
@Component
public class PayCmdExe {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private PayConfig payConfig;


    public SingleResponse<CreatedPayVO> createPay(CreatePayCmd cmd) {
        //独立运行的情况，回调地址必须设置
        if(payConfig.getStartWeb() && StringUtils.isEmpty(payConfig.getNotifyUrl())){
            logger.error("没有设置支付回调地址,startWeb:[{}],notify:[{}]",payConfig.getStartWeb(),payConfig.getNotifyUrl());
            return SingleResponse.buildFailure(ErrorCode.CREATED_PAY_BODY.getErrCode(), ErrorCode.CREATED_PAY_BODY.getErrDesc());
        }

        //校验参数
        extensionExecutor.executeVoid(CreatePayValidatorExtPt.class, cmd.getBizScenario(), extension -> extension.validate(cmd));

        //生成领域对象
        PayE payE = extensionExecutor.execute(CreatePayConvertorExtPt.class, cmd.getBizScenario(), convertor -> convertor.clientToEntity(cmd.getPayDTO(), cmd.getBizScenario()));
        ECreatePayStatus eCreatePayStatus=payE.pay();

        //支付，已成功
        if(eCreatePayStatus==ECreatePayStatus.HAS_PAY_SUCCESS){
            CreatedPayVO createdPayVO=new CreatedPayVO();
            createdPayVO.setStatus(eCreatePayStatus);
            return SingleResponse.of(createdPayVO);
        }

        //创建失败
        if(eCreatePayStatus==ECreatePayStatus.FAIL){
            return SingleResponse.buildFailure(ErrorCode.CREATED_PAY_BODY.getErrCode(), ErrorCode.CREATED_PAY_BODY.getErrDesc());
        }

        //调用第三方获取支付信息
        CreatedPayVO payRequestBody = extensionExecutor.execute(CreatePayThirdPartyExtPt.class, cmd.getBizScenario(), thirdparty -> thirdparty.created(cmd.getPayDTO()));

        //第三方创建支付信息失败。
        if (payRequestBody.getStatus() == ECreatePayStatus.FAIL) {
            CreatedPayExceptionEvent createdPayExceptionEvent = new CreatedPayExceptionEvent();
            createdPayExceptionEvent.setOutTradeNo(payE.getOutTradeNo());
            createdPayExceptionEvent.setOrderPayNo(payE.getOrderPayNo());
            domainEventPublisher.publish(createdPayExceptionEvent);

            payE.setStatus(ERecordPayStatus.FAILED);
            payE.payFail();
            return SingleResponse.buildFailure(ErrorCode.CREATED_PAY_BODY.getErrCode(), ErrorCode.CREATED_PAY_BODY.getErrDesc());
        }

        ;
        //创建支付成功
        return SingleResponse.of(payRequestBody);
    }
}
