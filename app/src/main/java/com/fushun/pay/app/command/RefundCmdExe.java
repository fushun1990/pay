package com.fushun.pay.app.command;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.app.common.exception.ErrorCode;
import com.fushun.pay.app.convertor.extensionpoint.RefundConvertorExtPt;
import com.fushun.pay.app.dto.RefundCmd;
import com.fushun.pay.dto.clientobject.RefundCO;
import com.fushun.pay.app.dto.enumeration.ERefundStatus;
import com.fushun.pay.app.thirdparty.extensionpoint.RefundThirdPartyExtPt;
import com.fushun.pay.app.validator.extensionpoint.RefundValidatorExtPt;
import com.fushun.pay.domain.refund.entity.RefundE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月03日22时37分
 */
@Component
public class RefundCmdExe{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtensionExecutor extensionExecutor;


    public Response execute(RefundCmd cmd) {

        //1, validation
        extensionExecutor.executeVoid(RefundValidatorExtPt.class, cmd.getBizScenario(), validator -> validator.validate(cmd));

        //2, invoke domain service or directly operate domain to do business logic process
        RefundE refundE = extensionExecutor.execute(RefundConvertorExtPt.class, cmd.getBizScenario(), convertor -> convertor.clientToEntity(cmd.getRefundCO(),cmd.getBizScenario()));
        refundE.refund();
        cmd.getRefundCO().setPayMoney(refundE.getPayMoney());

        //获取支付信息
        try {
            RefundCO refundCO = extensionExecutor.execute(RefundThirdPartyExtPt.class, cmd.getBizScenario(), thirdparty -> thirdparty.refund(cmd.getRefundCO()));
        } catch (Exception e) {
            logger.error("refund exception,param:[{}]", JsonUtil.toJson(cmd.getRefundCO()), e);
            refundE.setERefundStatus(ERefundStatus.FAIL);
            refundE.setResult(e.getMessage());
            refundE.fail();
            return Response.buildFailure(ErrorCode.REFUND_FAIL.getErrCode(),ErrorCode.REFUND_FAIL.getErrDesc());
        }

        refundE.setThirdRefundNo(cmd.getRefundCO().getThirdRefundNo());
        refundE.setERefundStatus(ERefundStatus.SUCCESS);
        refundE.success();

        return Response.buildSuccess();
    }
}
