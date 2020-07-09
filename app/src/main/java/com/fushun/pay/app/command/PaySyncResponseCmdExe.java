package com.fushun.pay.app.command;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.BeanUtils;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.framework.util.util.StringUtils;
import com.fushun.pay.app.common.exception.ErrorCode;
import com.fushun.pay.app.convertor.extensionpoint.PaySyncResponseConvertorExtPt;
import com.fushun.pay.app.thirdparty.extensionpoint.PaySyncResponseThirdPartyExtPt;
import com.fushun.pay.app.validator.extensionpoint.PaySyncResponseValidatorExtPt;
import com.fushun.pay.client.dto.PaySyncResponseCmd;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.client.dto.domainevent.PaySyncResponseExceptionEvent;
import com.fushun.pay.domain.exception.BasePayException;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.infrastructure.common.util.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付同步返回校验
 * @creation 2019年01月23日23时36分
 */
@Component
public class PaySyncResponseCmdExe{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    public SingleResponse<String> execute(PaySyncResponseCmd cmd) {

        //解析 通知信息
        PaySyncResponseDTO paySyncResponseDTO = null;
        boolean analysisSyncResponse = true;
        String errorCode=null;
        try {
            paySyncResponseDTO = extensionExecutor.execute(PaySyncResponseThirdPartyExtPt.class, cmd.getBizScenario(), thirdparty -> thirdparty.responseValidator(cmd.getPaySyncResponseValidatorTO()));
        }catch (BasePayException e){
            analysisSyncResponse = false;
            errorCode=e.getErrorCode();
            logger.error("pay syncResponse fail,PaySyncResponseCO:[{}]", JsonUtil.classToJson(cmd.getPaySyncResponseValidatorTO()), e);
        }catch (Exception e) {
            analysisSyncResponse = false;
            logger.error("pay syncResponse fail,PaySyncResponseCO:[{}]", JsonUtil.classToJson(cmd.getPaySyncResponseValidatorTO()), e);

            PaySyncResponseExceptionEvent paySyncResponseExceptionEvent = new PaySyncResponseExceptionEvent();
            paySyncResponseExceptionEvent.setOutTradeNo(cmd.getPaySyncResponseValidatorTO().getOutTradeNo());
            domainEventPublisher.publish(paySyncResponseExceptionEvent);
        }

        //校验参数
        extensionExecutor.executeVoid(PaySyncResponseValidatorExtPt.class, cmd.getBizScenario(), validator -> validator.validate(cmd));

        //2, invoke domain service or directly operate domain to do business logic process
        PaySyncResponseDTO finalPaySyncResponseDTO = paySyncResponseDTO;
        PayE payE = extensionExecutor.execute(PaySyncResponseConvertorExtPt.class, cmd.getBizScenario(), convertor -> convertor.clientToEntity(finalPaySyncResponseDTO,cmd.getBizScenario()));
        payE.syncResponse();

        ERecordPayStatus eRecordPayStatus=paySyncResponseDTO.getStatus();
        if (!analysisSyncResponse || (BeanUtils.isNotNull(eRecordPayStatus) && eRecordPayStatus!=ERecordPayStatus.SUCCESS)) {
            return SingleResponse.buildFailure(StringUtils.isEmpty(errorCode)?ErrorCode.PAY_FAIL.getErrCode():errorCode, ErrorCode.PAY_FAIL.getErrCode());
        }
        return SingleResponse.of(ErrorCode.PAY_SUCCESS.getErrDesc());
    }
}
