package com.fushun.pay.start.restful;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.BizScenario;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.web.annotations.NoApiResult;
import com.fushun.framework.web.exception.BadRequestException;
import com.fushun.pay.app.api.PayServiceI;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHCO;
import com.fushun.pay.app.dto.cmd.syncresponse.PaySyncResponseWeiXinGZHCmd;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/valid")
public class ResponseValidatorController {

    @Autowired
    private PayServiceI payServiceI;

    /**
     * 公众号 支付同步返回校验
     * {"responseStr":"微信返回参数","orderPayNo":"订单支付单号"}
     * @param paySyncResponseWeixinGZHCO
     */
    @RequestMapping("/sync/gzh")
    @NoApiResult
    public ApiResult<String> payGZHResponseValidator(PaySyncResponseWeixinGZHCO paySyncResponseWeixinGZHCO){
        PaySyncResponseWeiXinGZHCmd paySyncResponseWeiXinGZHCmd=new PaySyncResponseWeiXinGZHCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.payScenario_WEIXIN_GZH);
        paySyncResponseWeiXinGZHCmd.setBizScenario(bizScenario);
        paySyncResponseWeiXinGZHCmd.setPaySyncResponseWeixinGZHCO(paySyncResponseWeixinGZHCO);
        SingleResponse<String> singleResponse= payServiceI.payResponseValidator(paySyncResponseWeiXinGZHCmd);
        if(!singleResponse.isSuccess()){
            return ApiResult.ofFail(singleResponse.getErrCode(),singleResponse.getErrMessage());
        }
        return ApiResult.of(singleResponse.getData());
    }

}
