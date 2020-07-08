package com.fushun.pay.thirdparty.weixin.pay;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.DateUtil;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.app.dto.clientobject.NotifyReturnDTO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayWeiXinAppCO;
import com.fushun.pay.app.dto.clientobject.createpay.EStatus;
import com.fushun.pay.app.dto.clientobject.createpay.response.CreatePayWeiXinAppVO;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyWeixinAppCO;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseWeixinAppCO;
import com.fushun.pay.app.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.domain.exception.PayException;
import com.tencent.common.AppCConfigure;
import com.tencent.common.Signature;
import com.tencent.protocol.apppay_protocol.AppPayReqData;
import com.tencent.protocol.jspay_protocol.NotifyResData;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
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
    public CreatePayWeiXinAppVO getRequest(CreatePayWeiXinAppCO payParamDTO) {
        //下单是不，更新订单为支付失败
        CreatePayWeiXinAppVO createdPayThirdPartyCO = new CreatePayWeiXinAppVO();
        createdPayThirdPartyCO.setStatus(EStatus.SUCCESS);
        try {
            getRequestData(payParamDTO,createdPayThirdPartyCO);
            return createdPayThirdPartyCO;
        } catch (Exception e) {
            createdPayThirdPartyCO.setStatus(EStatus.FAIL);
            logger.warn("created pay error,payParamDTO:[{}]", payParamDTO.toString(), e);
//			payNotity.updatePayStatus(recordPayDTO, payParamDTO.getPayWay());
        }
        return createdPayThirdPartyCO;
    }

    /**
     * 参数
     *
     * @param payParamDTO
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    protected void getRequestData(CreatePayWeiXinAppCO payParamDTO,CreatePayWeiXinAppVO createdPayThirdPartyCO) {
        String outTradeNo = payParamDTO.getPayFrom().getPreStr() + payParamDTO.getTradeNo();
        UnifiedorderResData unifiedorderResData = null;
        unifiedorderResData = weiXinUnifiedOrderFacade.unifiedOrderPay(payParamDTO);

        AppPayReqData jsPayReqData = new AppPayReqData(AppCConfigure.initMethod());
        jsPayReqData.setTimestamp(String.valueOf(DateUtil.getTimestamp() / 1000));
        jsPayReqData.setPackage_("Sign=WXPay");
        jsPayReqData.setPrepayid(unifiedorderResData.getPrepay_id());
        jsPayReqData.setSign(Signature.getMD5Sign(Signature.getSignMap(AppPayReqData.class, jsPayReqData), jsPayReqData.getConfigure()));

        Map<String, String> map = new HashMap<String, String>();
        createdPayThirdPartyCO.setAppId(jsPayReqData.getAppid());
        createdPayThirdPartyCO.setPartnerid(jsPayReqData.getPartnerid());
        createdPayThirdPartyCO.setPrepayid(jsPayReqData.getPrepayid());
        createdPayThirdPartyCO.setPackage_(jsPayReqData.getPackage_());
        createdPayThirdPartyCO.setNoncestr(jsPayReqData.getNoncestr());
        createdPayThirdPartyCO.setTimestamp( jsPayReqData.getTimestamp());
        createdPayThirdPartyCO.setSign(jsPayReqData.getSign());
        createdPayThirdPartyCO.setOrderPayNo(outTradeNo);

    }


    protected String createPayHtml(Map<String, String> map) {
        return JsonUtil.toJson(map);
    }

    public void payNotifyAlipayReust(Map<String, String> requestParams, PayNotifyWeixinAppCO recordPayDTO) {
        recordPayDTO.setNotifyReturnDTO(WeChatAppPayFacade.notifyReturnDTO);
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(requestParams);
        if (!Signature.checkIsSignValidFromResponseString(map, AppCConfigure.initMethod())) {
            throw new PayException(PayException.PayExceptionEnum.SIGNATURE_VALIDATION_FAILED);
        }
        NotifyResData notifyResData = JsonUtil.hashMapToClass(map, NotifyResData.class);
        if ("FAIL".equals(notifyResData.getReturn_code())) {
            recordPayDTO.setStatus(ERecordPayStatus.FAILED);
            return;
        }
        recordPayDTO.setPayNo(notifyResData.getTransaction_id());
        recordPayDTO.setOutTradeNo(notifyResData.getOut_trade_no());
        recordPayDTO.setStatus(ERecordPayStatus.SUCCESS);
        recordPayDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(notifyResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
    }


    /**
     * @param requestParams 返回参数 {"result":"微信返回参数","orderPayNo":"订单支付单号"}
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     */
    public void payResultAlipayReust(String requestParams, PaySyncResponseWeixinAppCO recordPayDTO) {
        Map<String, Object> map = JsonUtil.jsonToHashMap(requestParams);
        if (map == null || StringUtils.isEmpty(map.get("orderPayNo"))) {
            throw new PayException(PayException.PayExceptionEnum.PAY_FAILED);
        }
        recordPayDTO.setOutTradeNo(map.get("orderPayNo").toString());
        recordPayDTO.setStatus(ERecordPayStatus.FAILED);

        if (!"0".equals(map.get("result"))) {
            throw new PayException(PayException.PayExceptionEnum.PAY_FAILED);
        }
        recordPayDTO.setStatus(ERecordPayStatus.FAILED);

        OrderQueryResData orderQueryResData = weiXinPayQueryFacade.getOrderQuery(map.get("orderPayNo").toString(), AppCConfigure.initMethod());

        recordPayDTO.setPayNo(orderQueryResData.getTransaction_id());
        recordPayDTO.setOutTradeNo(orderQueryResData.getOut_trade_no());
        recordPayDTO.setStatus(ERecordPayStatus.SUCCESS);
        recordPayDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(orderQueryResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
    }

}
