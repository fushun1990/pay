package com.fushun.pay.app.command;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.app.convertor.extensionpoint.PayNotifyConvertorExtPt;
import com.fushun.pay.app.thirdparty.extensionpoint.PayNotifyThirdPartyExtPt;
import com.fushun.pay.app.validator.extensionpoint.PayNotifyValidatorExtPt;
import com.fushun.pay.client.config.PayConfig;
import com.fushun.pay.client.dto.PayNotifyCmd;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.client.dto.domainevent.AnalysisNotifyExceptionEvent;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.domain.pay.entity.PayE;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.infrastructure.common.util.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月21日23时08分
 */
@Component
public class PayNotifyCmdExe{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private PayConfig payConfig;


    public SingleResponse<String> execute(PayNotifyCmd cmd) {
        PayNotifyThirdPartyDTO payNotifyThirdPartyDTO = null;
        //异步通知，校验是否通过
        boolean analysisNotify = true;
        try {
            //解析 通知信息
            payNotifyThirdPartyDTO = extensionExecutor.execute(PayNotifyThirdPartyExtPt.class, cmd.getBizScenario(), thirdparty -> thirdparty.created(cmd.getPayNotifyDTO()));
        }catch(PayException e){
            if(!e.isPrinted()){
                logger.error("异步通知处理失败, paramMap:[{}]", JsonUtil.toJson(cmd.getPayNotifyDTO()), e);
            }
            analysisNotify = false;
            AnalysisNotifyExceptionEvent analysisNotifyExceptionEvent = new AnalysisNotifyExceptionEvent();
            analysisNotifyExceptionEvent.setOutTradeNo(payNotifyThirdPartyDTO.getOutTradeNo());
            domainEventPublisher.publish(analysisNotifyExceptionEvent);
        } catch (Exception e) {
            analysisNotify = false;
            logger.error("异步通知处理失败, paramMap:[{}]", JsonUtil.toJson(cmd.getPayNotifyDTO()), e);

            AnalysisNotifyExceptionEvent analysisNotifyExceptionEvent = new AnalysisNotifyExceptionEvent();
            analysisNotifyExceptionEvent.setOutTradeNo(payNotifyThirdPartyDTO.getOutTradeNo());
            domainEventPublisher.publish(analysisNotifyExceptionEvent);
        }

        //通知，异步通知出现异常，不做任何状态更新
        if(payNotifyThirdPartyDTO.getStatus()== ERecordPayStatus.EXCEPTION){
            return SingleResponse.of(payNotifyThirdPartyDTO.getNotifyReturnDTO().getFail());
        }

        try {
            //验证参数
            PayNotifyThirdPartyDTO finalPayNotifyThirdPartyDTO = payNotifyThirdPartyDTO;
            extensionExecutor.executeVoid(PayNotifyValidatorExtPt.class, cmd.getBizScenario(), validator -> validator.validate(finalPayNotifyThirdPartyDTO));

            //转换为 领域对象
            PayNotifyThirdPartyDTO finalPayNotifyThirdPartyDTO1 = payNotifyThirdPartyDTO;
            PayE payE = extensionExecutor.execute(PayNotifyConvertorExtPt.class, cmd.getBizScenario(), convertor -> convertor.clientToEntity(finalPayNotifyThirdPartyDTO1,cmd.getBizScenario()));
            payE.payNotify();
        } catch (Exception e) {
            analysisNotify = false;
            logger.error("pay notify fail, paramMap:[{}]", JsonUtil.toJson(cmd.getPayNotifyDTO()), e);
        }


        if (!analysisNotify) {
            return SingleResponse.of(payNotifyThirdPartyDTO.getNotifyReturnDTO().getFail());
        }
        //3, response
        return SingleResponse.of(payNotifyThirdPartyDTO.getNotifyReturnDTO().getSuccess());

    }

}
