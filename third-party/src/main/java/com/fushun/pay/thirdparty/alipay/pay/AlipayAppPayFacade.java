package com.fushun.pay.thirdparty.alipay.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.fushun.framework.util.util.DESUtil;
import com.fushun.framework.util.util.EnumUtil;
import com.fushun.framework.util.util.ExceptionUtils;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.client.config.PayConfig;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyAlipayAppDTO;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseAlipayAppDTO;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.dto.clientobject.NotifyReturnDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayAppDTO;
import com.fushun.pay.dto.clientobject.createpay.enumeration.ECreatePayStatus;
import com.fushun.pay.dto.clientobject.createpay.response.CreatePayAliPayAppVO;
import com.fushun.pay.dto.clientobject.notify.PayNotifyAlipayAppDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseAlipayAppValidatorDTO;
import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.thirdparty.sdk.alipay.config.AlipayConfig;
import com.fushun.pay.thirdparty.sdk.alipay.enumeration.ETradeStatus;
import com.fushun.pay.thirdparty.weixin.pay.exception.PayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 支付宝 app移动端  支付接口
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2016年9月10日
 */
@Component
public class AlipayAppPayFacade {

    /**
     * 返回对象
     */
    private static NotifyReturnDTO notifyReturnDTO;
    private static Map<String, String> map = new HashMap<String, String>();

    static {
        notifyReturnDTO = new NotifyReturnDTO();
        notifyReturnDTO.setFail("fail");
        notifyReturnDTO.setSuccess("success");
    }

    static {
        map.put("9000", "订单支付成功");
        map.put("8000", "正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态");
        map.put("4000", "订单支付失败");
        map.put("5000", "重复请求");
        map.put("6001", "用户中途取消");
        map.put("6002", "网络连接出错");
        map.put("6004", "支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态");
//		其它	其它支付错误
    }

    @Autowired
    AlipayConfig alipayConfig;

    @Autowired
    private PayConfig payConfig;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取支付参数
     *
     * @param payParamDTO
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月18日
     * @records <p>  fushun 2017年1月18日</p>
     */
    public CreatePayAliPayAppVO getRequest(CreatePayAlipayAppDTO payParamDTO) {
        //下单是不，更新订单为支付失败
        CreatePayAliPayAppVO createPayAliPayAppVO = new CreatePayAliPayAppVO();
        createPayAliPayAppVO.setStatus(ECreatePayStatus.SUCCESS);
        try {
            Map<String, String> map = this.getRequestData(payParamDTO);
            String payStr = createPayHtml(map);
            createPayAliPayAppVO.setOutTradeNo(map.get("outTradeNo"));
            createPayAliPayAppVO.setPayStr(payStr);
        } catch (Exception e) {
            createPayAliPayAppVO.setStatus(ECreatePayStatus.FAIL);
            logger.warn("created pay error,payParamDTO:[{}]", payParamDTO.toString(), e);
        }
        return createPayAliPayAppVO;
    }

    private Map<String, String> getRequestData(CreatePayAlipayAppDTO payParamDTO) {
        String outTradeNo = payParamDTO.getPayFrom().getPreStr() + payParamDTO.getOrderPayNo();

        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();

        if(payConfig.getStartWeb()){
            sParaTemp.put("notify_url", payConfig.getNotifyUrl());
        }else{
            sParaTemp.put("notify_url", payParamDTO.getNotifyUrl());
        }


        Map<String, Object> map = new HashMap<String, Object>();

        map.put("subject", payParamDTO.getSubject());
        map.put("out_trade_no", outTradeNo);
        map.put("timeout_express", "30m");
        map.put("seller_id", alipayConfig.getPartner());
        map.put("total_amount", payParamDTO.getTotalFee().toPlainString());
        map.put("product_code", "QUICK_MSECURITY_PAY");
        if (payParamDTO.getBody() != null) {
            map.put("body", payParamDTO.getBody());
        }
        sParaTemp.put("biz_content", JsonUtil.classToJson(map));
        sParaTemp.put("outTradeNo", outTradeNo);
        return sParaTemp;
    }

    private String createPayHtml(Map<String, String> map) {

        AlipayClient client = new DefaultAlipayClient(alipayConfig.getGateway(), alipayConfig.getAppId(), alipayConfig.getRsaPrivateKeyPkcs8(), "json", alipayConfig.getInputCharset(), alipayConfig.getAliPayPublicKey(), alipayConfig.getSignType());
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();//创建API对应的request
        alipayRequest.setNotifyUrl(map.get("notify_url"));//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent(map.get("biz_content"));//填充业务参数
        try {
            String form = client.sdkExecute(alipayRequest).getBody();//app.pageExecute(alipayRequest).getBody();
            return form;
        } catch (AlipayApiException e) {
            throw new PayException(e, PayException.PayExceptionEnum.PAY_CREATE_FAILED);
        } //调用SDK生成表单
//		return AlipaySubmitApp.buildRequest(map,AlipayConfig.RSA_PRIVATE);
    }

    public PayNotifyThirdPartyDTO payNotifyAlipayReust(PayNotifyAlipayAppDTO recordPayDTO) {
        Map<String, String> requestParams=recordPayDTO.getParamMap();
        PayNotifyThirdPartyAlipayAppDTO payNotifyThirdPartyDTO=new PayNotifyThirdPartyAlipayAppDTO();
        payNotifyThirdPartyDTO.setEPayWay(EPayWay.PAY_WAY_ALIPAY);
        payNotifyThirdPartyDTO.setReceiveWay(EPayWay.PAY_WAY_ALIPAY);
        payNotifyThirdPartyDTO.setNotifyReturnDTO(AlipayAppPayFacade.notifyReturnDTO);
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号

        String out_trade_no = params.get("out_trade_no").toString();

        // 支付宝交易号
        String trade_no = params.get("trade_no").toString();

        // 交易状态
        String trade_status = params.get("trade_status").toString();
        String seller_email = null;
        if (!StringUtils.isEmpty(params.get("seller_email"))) {
            seller_email = params.get("seller_email").toString();
            try {
                payNotifyThirdPartyDTO.setReceiveAccourt(DESUtil.encrypt(seller_email, DESUtil.DES_KEY));
            } catch (Exception e) {
            }
        }

        BigDecimal total_fee = null;
        if (!StringUtils.isEmpty(requestParams.get("total_amount"))) {
            total_fee = BigDecimal.valueOf(Double.valueOf(requestParams.get("total_amount").toString()));
            payNotifyThirdPartyDTO.setPayMoney(total_fee);
        }


        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAliPayPublicKey(), alipayConfig.getInputCharset());
        } catch (AlipayApiException e) {
            ExceptionUtils.rethrow(new PayException(e, PayException.PayExceptionEnum.SIGNATURE_VALIDATION_FAILED),logger,"异步通知，签名验证异常，params:[{}]",JsonUtil.toJson(params));
        }
        if (!signVerified) {
            ExceptionUtils.rethrow(new PayException(PayException.PayExceptionEnum.PAY_FAILED),logger,"异步通知，签名验证失败，params:[{}]",JsonUtil.toJson(params));
        }

        // ////////////////////////////////////////////////////////////////////////////////////////
        // 请在这里加上商户的业务逻辑程序代码

        payNotifyThirdPartyDTO.setOutTradeNo(out_trade_no);
        payNotifyThirdPartyDTO.setPayNo(trade_no);
        ETradeStatus tradeStatus = EnumUtil.getEnumByName(ETradeStatus.class, trade_status);
        if (tradeStatus == null) {
            throw new PayException(null, PayException.PayExceptionEnum.ALIPAY_ORDER_STATUS);
        }
        switch (tradeStatus) {
            case WAIT_BUYER_PAY:
                payNotifyThirdPartyDTO.setStatus(ERecordPayStatus.FAILED);
                break;
            case TRADE_CLOSED:
                payNotifyThirdPartyDTO.setStatus(ERecordPayStatus.FAILED);
                break;
            case TRADE_SUCCESS:
                payNotifyThirdPartyDTO.setStatus(ERecordPayStatus.SUCCESS);
                break;
            case TRADE_FINISHED:
                payNotifyThirdPartyDTO.setStatus(ERecordPayStatus.SUCCESS);
                break;
            default:
                ExceptionUtils.rethrow(new PayException(null, PayException.PayExceptionEnum.PAY_BUSINESS),logger,"异步通知，支付状态解析异常，tradeStatus:[[]],param:[{}]",tradeStatus.getCode(),JsonUtil.toJson(params));
        }

        return payNotifyThirdPartyDTO;

    }

    /**
     * 同步返回信息验证
     *
     * @param paySyncResponseAlipayAppValidatorDTO
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月17日
     * @records <p>  fushun 2016年9月17日</p>
     */
    public PaySyncResponseDTO payResultAlipayReust(PaySyncResponseAlipayAppValidatorDTO paySyncResponseAlipayAppValidatorDTO) {

        PaySyncResponseAlipayAppDTO paySyncResponseDTO=new PaySyncResponseAlipayAppDTO();
        Map<String, Object> requestParamsMap = JsonUtil.jsonToHashMap(paySyncResponseAlipayAppValidatorDTO.getResponseStr());
        Map<String, Object> resultmap = JsonUtil.jsonToHashMap(requestParamsMap.get("result").toString());
        String resultStatus = (String) resultmap.get("resultStatus");

        paySyncResponseDTO.setOutTradeNo(String.valueOf(paySyncResponseAlipayAppValidatorDTO.getOutTradeNo()));
        paySyncResponseDTO.setStatus(ERecordPayStatus.FAILED);
        paySyncResponseDTO.setEPayWay(EPayWay.PAY_WAY_ALIPAY);
        paySyncResponseDTO.setReceiveWay(EPayWay.PAY_WAY_ALIPAY);

        if (StringUtils.isEmpty(resultStatus)) {
            throw new PayException(null, PayException.PayExceptionEnum.PAY_RETURN_STATUS_ERROR);
        }

        if (!resultStatus.equals("9000")) {
            getErrorCode(resultStatus);
            return null;
        }

        Map<String, Object> map = JsonUtil.jsonToHashMap(String.valueOf(resultmap.get("result")));

        Map<String, String> map2 = (Map<String, String>) map.get("alipay_trade_app_pay_response");

        paySyncResponseDTO.setOutTradeNo( paySyncResponseAlipayAppValidatorDTO.getOutTradeNo());

        // 交易状态
        String code = map2.get("code");
        String charset = map2.get("charset");
        String sign = map.get("sign").toString();
        String signType = map.get("sign_type").toString();
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheck(JsonUtil.classToJson(map2), sign
                    , alipayConfig.getAliPayPublicKey(), charset, signType);
        } catch (AlipayApiException e) {
        }
        if (!signVerified) {
            throw new PayException(null, PayException.PayExceptionEnum.SIGNATURE_VALIDATION_FAILED);
        }
        // 验证成功
        // ////////////////////////////////////////////////////////////////////////////////////////
        // 请在这里加上商户的业务逻辑程序代码
        if (!code.equals("10000")) {
            throw new PayException(null, PayException.PayExceptionEnum.PAY_CODE_ERROR);
        }


        // 支付宝交易号
        String trade_no = map2.get("trade_no").toString();
        double total_fee = Double.valueOf(map2.get("total_amount").toString());
        paySyncResponseDTO.setPayNo(trade_no);
        paySyncResponseDTO.setPayMoney(BigDecimal.valueOf(total_fee));
        paySyncResponseDTO.setStatus(ERecordPayStatus.SUCCESS);
        return paySyncResponseDTO;
    }

    /**
     * 获取支付错误新
     *
     * @param code
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月17日
     * @records <p>  fushun 2016年9月17日</p>
     */
    private String getErrorCode(String code) {
        String error = map.get(code);
        if (!StringUtils.isEmpty(error)) {
            throw new PayException(PayException.PayExceptionEnum.PAY_FAILED,error);
        }
        throw new PayException(null, PayException.PayExceptionEnum.PAY_FAILED);
    }

}
