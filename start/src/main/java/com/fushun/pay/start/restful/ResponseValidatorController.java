package com.fushun.pay.start.restful;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.BizScenario;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.web.annotations.NoApiResult;
import com.fushun.pay.client.api.PayServiceI;
import com.fushun.pay.client.dto.cmd.syncresponse.PaySyncResponseWeiXinGZHCmd;
import com.fushun.pay.client.dto.cmd.syncresponse.PaySyncResponseWeiXinXCXCmd;
import com.fushun.pay.dto.clientobject.PaySyncResponseValidatorDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHValidatorDTO;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/valid")
public class ResponseValidatorController {

    @Autowired
    private PayServiceI payServiceI;

    /**
     * 公众号 支付同步返回校验
     * @param paySyncResponseWeixinGZHValidatorDTO
     */
    @RequestMapping("/sync/weixin/gzh")
    @NoApiResult
    public ApiResult<String> payGZHResponseValidator(@RequestBody PaySyncResponseWeixinGZHValidatorDTO paySyncResponseWeixinGZHValidatorDTO){
        PaySyncResponseWeiXinGZHCmd paySyncResponseWeiXinGZHCmd=new PaySyncResponseWeiXinGZHCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_WEIXIN_GZH);
        paySyncResponseWeiXinGZHCmd.setBizScenario(bizScenario);
        paySyncResponseWeiXinGZHCmd.setPaySyncResponseWeixinGZHValidatorDTO(paySyncResponseWeixinGZHValidatorDTO);
        SingleResponse<String> singleResponse= payServiceI.payResponseValidator(paySyncResponseWeiXinGZHCmd);
        if(!singleResponse.isSuccess()){
            return ApiResult.ofFail(singleResponse.getErrCode(),singleResponse.getErrMessage());
        }
        return ApiResult.of(singleResponse.getData());
    }

    /**
     * 微信小程序 支付同步返回校验
     * @param paySyncResponseValidatorDTO
     */
    @RequestMapping("/sync/weixin/xcx")
    @NoApiResult
    public ApiResult<String> payXCXResponseValidator(@RequestBody PaySyncResponseValidatorDTO paySyncResponseValidatorDTO){
        PaySyncResponseWeiXinXCXCmd paySyncResponseWeiXinGZHCmd=new PaySyncResponseWeiXinXCXCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_WEIXIN_XCX);
        paySyncResponseWeiXinGZHCmd.setBizScenario(bizScenario);
        paySyncResponseWeiXinGZHCmd.setPaySyncResponseValidatorDTO(paySyncResponseValidatorDTO);
        SingleResponse<String> singleResponse= payServiceI.payResponseValidator(paySyncResponseWeiXinGZHCmd);
        if(!singleResponse.isSuccess()){
            return ApiResult.ofFail(singleResponse.getErrCode(),singleResponse.getErrMessage());
        }
        return ApiResult.of(singleResponse.getData());
    }

}
