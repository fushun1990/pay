package com.fushun.pay.thirdparty.weixin.pay;

import com.fushun.framework.util.util.DateUtil;
import com.fushun.framework.util.util.ExceptionUtils;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyWeixinAppDTO;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseWeixinAppDTO;
import com.fushun.pay.dto.clientobject.NotifyReturnDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayWeiXinAppDTO;
import com.fushun.pay.dto.clientobject.createpay.enumeration.ECreatePayStatus;
import com.fushun.pay.dto.clientobject.createpay.response.CreatePayWeiXinAppVO;
import com.fushun.pay.dto.clientobject.notify.PayNotifyWeixinAppDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseWeixinAppValidatorDTO;
import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.thirdparty.weixin.pay.exception.PayException;
import com.tencent.common.AppCConfigure;
import com.tencent.common.Signature;
import com.tencent.protocol.apppay_protocol.AppPayReqData;
import com.tencent.protocol.jspay_protocol.NotifyResData;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信app支付
 *
 * @author fushun
 * @version devlogin
 * @creation 2017年5月27日
 */
@Component
public class WeChatAppPayFacade {

    /**
     * 返回对象
     */
    private static NotifyReturnDTO notifyReturnDTO;

    static {
        notifyReturnDTO = new NotifyReturnDTO();
        notifyReturnDTO.setFail("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
        notifyReturnDTO.setSuccess("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WeiXinUnifiedOrderFacade weiXinUnifiedOrderFacade;
    @Autowired
    private WeiXinPayQueryFacade weiXinPayQueryFacade;

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
    public CreatePayWeiXinAppVO getRequest(CreatePayWeiXinAppDTO payParamDTO) {
        //下单是不，更新订单为支付失败
        CreatePayWeiXinAppVO createdPayThirdPartyCO = new CreatePayWeiXinAppVO();
        createdPayThirdPartyCO.setStatus(ECreatePayStatus.SUCCESS);
        try {
            this.getRequestData(payParamDTO,createdPayThirdPartyCO);
            return createdPayThirdPartyCO;
        } catch (Exception e) {
            createdPayThirdPartyCO.setStatus(ECreatePayStatus.FAIL);
            logger.warn("created pay error,payParamDTO:[{}]", payParamDTO.toString(), e);
//			payNotity.updatePayStatus(recordPayDTO, payParamDTO.getPayWay());
        }
        return createdPayThirdPartyCO;
    }

    /**
     * 获取支付参数
     *
     * @param payParamDTO
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    protected void getRequestData(CreatePayWeiXinAppDTO payParamDTO, CreatePayWeiXinAppVO createPayWeiXinAppVO) {
        String outTradeNo = payParamDTO.getPayFrom().getPreStr() + payParamDTO.getOrderPayNo();
        UnifiedorderResData unifiedorderResData = null;
        unifiedorderResData = weiXinUnifiedOrderFacade.unifiedOrderPay(payParamDTO);

        AppPayReqData jsPayReqData = new AppPayReqData(AppCConfigure.initMethod());
        jsPayReqData.setTimestamp(String.valueOf(DateUtil.getTimestamp() / 1000));
        jsPayReqData.setPackage_("Sign=WXPay");
        jsPayReqData.setPrepayid(unifiedorderResData.getPrepay_id());
        jsPayReqData.setSign(Signature.getMD5Sign(Signature.getSignMap(AppPayReqData.class, jsPayReqData), jsPayReqData.getConfigure()));

        Map<String, String> map = new HashMap<String, String>();
        createPayWeiXinAppVO.setAppId(jsPayReqData.getAppid());
        createPayWeiXinAppVO.setPartnerid(jsPayReqData.getPartnerid());
        createPayWeiXinAppVO.setPrepayid(jsPayReqData.getPrepayid());
        createPayWeiXinAppVO.setPackage_(jsPayReqData.getPackage_());
        createPayWeiXinAppVO.setNoncestr(jsPayReqData.getNoncestr());
        createPayWeiXinAppVO.setTimestamp( jsPayReqData.getTimestamp());
        createPayWeiXinAppVO.setSign(jsPayReqData.getSign());
        createPayWeiXinAppVO.setOutTradeNo(outTradeNo);

    }

    /**
     * 支付成功，异步校验支付状态
     * @param recordPayDTO
     */
    public PayNotifyThirdPartyWeixinAppDTO payNotifyAlipayReust(PayNotifyWeixinAppDTO recordPayDTO) {
        PayNotifyThirdPartyWeixinAppDTO payNotifyThirdPartyWeixinAppDTO=new PayNotifyThirdPartyWeixinAppDTO();
        Map<String, String> requestParams=recordPayDTO.getParamMap();

        payNotifyThirdPartyWeixinAppDTO.setEPayWay(EPayWay.PAY_WAY_APPC_WEIXINPAY);
        payNotifyThirdPartyWeixinAppDTO.setReceiveWay(EPayWay.PAY_WAY_APPC_WEIXINPAY);
        payNotifyThirdPartyWeixinAppDTO.setNotifyReturnDTO(WeChatAppPayFacade.notifyReturnDTO);
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(requestParams);
        if (!Signature.checkIsSignValidFromResponseString(map, AppCConfigure.initMethod())) {
            ExceptionUtils.rethrow(new PayException(PayException.PayExceptionEnum.SIGNATURE_VALIDATION_FAILED),logger,"微信app 签名失败，params:[{}]",JsonUtil.toJson(requestParams));
        }
        NotifyResData notifyResData = JsonUtil.hashMapToClass(map, NotifyResData.class);
        if ("FAIL".equals(notifyResData.getReturn_code())) {
            payNotifyThirdPartyWeixinAppDTO.setStatus(ERecordPayStatus.FAILED);
            return payNotifyThirdPartyWeixinAppDTO;
        }
        payNotifyThirdPartyWeixinAppDTO.setPayNo(notifyResData.getTransaction_id());
        payNotifyThirdPartyWeixinAppDTO.setOutTradeNo(notifyResData.getOut_trade_no());
        payNotifyThirdPartyWeixinAppDTO.setStatus(ERecordPayStatus.SUCCESS);
        payNotifyThirdPartyWeixinAppDTO.setPayMoney(BigDecimal.valueOf(Double.parseDouble(notifyResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
        return payNotifyThirdPartyWeixinAppDTO;
    }


    /**
     * 支付成功，同步校验状态
     * @param
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     */
    public PaySyncResponseDTO payResultAlipayReust(PaySyncResponseWeixinAppValidatorDTO paySyncResponseWeixinAppValidatorDTO) {
        PaySyncResponseWeixinAppDTO paySyncResponseDTO =new PaySyncResponseWeixinAppDTO();
        Map<String, Object> map = JsonUtil.jsonToHashMap(paySyncResponseWeixinAppValidatorDTO.getResponseStr());
        if (map == null || StringUtils.isEmpty(paySyncResponseWeixinAppValidatorDTO.getOutTradeNo())) {
            throw new PayException(PayException.PayExceptionEnum.PAY_FAILED);
        }
        paySyncResponseDTO.setOutTradeNo(paySyncResponseWeixinAppValidatorDTO.getOutTradeNo());
        paySyncResponseDTO.setStatus(ERecordPayStatus.FAILED);

        if (!"0".equals(map.get("result"))) {
            throw new PayException(PayException.PayExceptionEnum.PAY_FAILED);
        }
        paySyncResponseDTO.setStatus(ERecordPayStatus.FAILED);

        OrderQueryResData orderQueryResData = weiXinPayQueryFacade.getOrderQuery(paySyncResponseWeixinAppValidatorDTO.getOutTradeNo(), AppCConfigure.initMethod());

        paySyncResponseDTO.setPayNo(orderQueryResData.getTransaction_id());
        paySyncResponseDTO.setOutTradeNo(orderQueryResData.getOut_trade_no());
        paySyncResponseDTO.setStatus(ERecordPayStatus.SUCCESS);
        paySyncResponseDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(orderQueryResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));

        return paySyncResponseDTO;
    }

}
