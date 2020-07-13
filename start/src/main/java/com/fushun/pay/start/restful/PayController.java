package com.fushun.pay.start.restful;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.BizScenario;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.web.annotations.NoApiResult;
import com.fushun.pay.client.api.PayServiceI;
import com.fushun.pay.client.dto.cmd.createdpay.CreatePayAlipayAppCmd;
import com.fushun.pay.client.dto.cmd.createdpay.CreatePayAlipayWapCmd;
import com.fushun.pay.client.dto.cmd.createdpay.CreatePayWeixinAppCmd;
import com.fushun.pay.client.dto.cmd.createdpay.CreatePayWeixinGZHCmd;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayAppDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayWapDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayWeiXinAppDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayWeiXinGZHDTO;
import com.fushun.pay.dto.clientobject.createpay.response.*;
import com.fushun.pay.infrastructure.common.BizCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年03月15日22时37分
 */
@RestController
public class PayController {

    @Autowired
    private PayServiceI payServiceI;

    /**
     * 支付宝 app
     * @param createPayAlipayAppDTO
     * @return com.fushun.pay.dto.clientobject.createpay.CreatedPayRequestBodyCO
     * @description
     * @date 2019年03月15日22时43分
     * @author wangfushun
     * @version 1.0
     */
    @PostMapping("/pay/alipay/app")
    @NoApiResult
    public ApiResult<CreatePayAliPayAppVO> createdPay(@RequestBody CreatePayAlipayAppDTO createPayAlipayAppDTO) {
        CreatePayAlipayAppCmd createPayAlipayAppCmd = new CreatePayAlipayAppCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_ALIPAY_APP);
        createPayAlipayAppCmd.setBizScenario(bizScenario);
        createPayAlipayAppCmd.setCreatePayAlipayAppDTO(createPayAlipayAppDTO);
        SingleResponse<CreatedPayVO> singleResponse= payServiceI.createPay(createPayAlipayAppCmd);
        if(!singleResponse.isSuccess()){
            return ApiResult.ofFail(singleResponse.getErrCode(),singleResponse.getErrMessage());
        }
        return ApiResult.of((CreatePayAliPayAppVO)singleResponse.getData());
    }

    /**
     * 支付宝 wap
     * @param createPayAlipayWapDTO
     * @return
     */
    @PostMapping("/pay/alipay/wap")
    @NoApiResult
    public ApiResult<CreatePayAliPayWapVO> createdAlipayWay(@RequestBody CreatePayAlipayWapDTO createPayAlipayWapDTO) {
        CreatePayAlipayWapCmd createPayAlipayWapCmd = new CreatePayAlipayWapCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_ALIPAY_WAP);
        createPayAlipayWapCmd.setBizScenario(bizScenario);
        createPayAlipayWapCmd.setCreatePayAlipayWapDTO(createPayAlipayWapDTO);
        SingleResponse<CreatedPayVO> singleResponse= payServiceI.createPay(createPayAlipayWapCmd);
        if(!singleResponse.isSuccess()){
            return ApiResult.ofFail(singleResponse.getErrCode(),singleResponse.getErrMessage());
        }
        return ApiResult.of((CreatePayAliPayWapVO)singleResponse.getData());
    }

    /**
     * 微信 app支付
     * @param createPayWeiXinAppDTO
     * @return
     */
    @PostMapping("/pay/weixin/app")
    @NoApiResult
    public ApiResult<CreatePayWeiXinAppVO> createdWeixinGZH(@RequestBody CreatePayWeiXinAppDTO createPayWeiXinAppDTO){
        CreatePayWeixinAppCmd createPayAlipayWapCmd = new CreatePayWeixinAppCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_WEIXIN_APP);
        createPayAlipayWapCmd.setBizScenario(bizScenario);
        createPayAlipayWapCmd.setCreatePayWeiXinAppDTO(createPayWeiXinAppDTO);
        SingleResponse<CreatedPayVO> singleResponse= payServiceI.createPay(createPayAlipayWapCmd);
        if(!singleResponse.isSuccess()){
            return ApiResult.ofFail(singleResponse.getErrCode(),singleResponse.getErrMessage());
        }
        return ApiResult.of((CreatePayWeiXinAppVO)singleResponse.getData());
    }

    /**
     * 微信公众号
     * @param createPayWeiXinGZHDTO
     * @return
     */
    @PostMapping("/pay/weixin/gzh")
    @NoApiResult
    public ApiResult<CreatePayWeiXinGZHVO> createdWeixinGZH(@RequestBody CreatePayWeiXinGZHDTO createPayWeiXinGZHDTO){
        CreatePayWeixinGZHCmd createPayAlipayWapCmd = new CreatePayWeixinGZHCmd();
        BizScenario bizScenario = BizScenario.valueOf(BizCode.payBizId, BizCode.payUseCase, BizCode.PAY_SCENARIO_WEIXIN_GZH);
        createPayAlipayWapCmd.setBizScenario(bizScenario);
        createPayAlipayWapCmd.setCreatePayWeiXinGZHDTO(createPayWeiXinGZHDTO);
        SingleResponse<CreatedPayVO> singleResponse= payServiceI.createPay(createPayAlipayWapCmd);
        if(!singleResponse.isSuccess()){
            return ApiResult.ofFail(singleResponse.getErrCode(),singleResponse.getErrMessage());
        }
        return ApiResult.of((CreatePayWeiXinGZHVO)singleResponse.getData());
    }

}
