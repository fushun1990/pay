package com.fushun.pay.thirdparty.weixin.pay;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.DateUtil;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.app.dto.clientobject.NotifyReturnDTO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatePayWeiXinGZHCO;
import com.fushun.pay.app.dto.clientobject.createpay.CreatedPayRequestBodyCO;
import com.fushun.pay.app.dto.clientobject.createpay.EStatus;
import com.fushun.pay.app.dto.clientobject.notify.PayNotifyWeixinGZHCO;
import com.fushun.pay.app.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHCO;
import com.fushun.pay.app.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.domain.exception.PayException;
import com.tencent.common.GZHConfigure;
import com.tencent.common.Signature;
import com.tencent.protocol.jspay_protocol.JsPayReqData;
import com.tencent.protocol.jspay_protocol.NotifyResData;
import com.tencent.protocol.oauth20_protocol.OAuth20ResData;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众号支付
 *
 * @author fushun
 * @version dev706
 * @creation 2017年5月27日
 */
@Component
public class WeChatGZHPayFacade {

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
    @Autowired
    private WeiXinOAuth20Facade weiXinOAuth20Facade;

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
    public CreatedPayRequestBodyCO getRequest(CreatePayWeiXinGZHCO payParamDTO) {
        //下单是不，更新订单为支付失败
        CreatedPayRequestBodyCO createdPayThirdPartyCO = new CreatedPayRequestBodyCO();
        createdPayThirdPartyCO.setStatus(EStatus.SUCCESS);
        try {
            Map<String, String> map = getRequestData(payParamDTO);
            String payStr = createPayHtml(map);
            createdPayThirdPartyCO.setPayStr(payStr);
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
    protected Map<String, String> getRequestData(CreatePayWeiXinGZHCO payParamDTO) {
        String outTradeNo = payParamDTO.getPayFrom().getPreStr() + payParamDTO.getTradeNo();
        UnifiedorderResData unifiedorderResData = null;
        unifiedorderResData = weiXinUnifiedOrderFacade.unifiedOrderPay(payParamDTO);

        JsPayReqData jsPayReqData = new JsPayReqData(GZHConfigure.initMethod());
        jsPayReqData.setTimeStamp(String.valueOf(DateUtil.getTimestamp() / 1000));
        jsPayReqData.setPackage_("prepay_id=" + unifiedorderResData.getPrepay_id());
        jsPayReqData.setSign(Signature.getMD5Sign(Signature.getSignMap(JsPayReqData.class, jsPayReqData), jsPayReqData.getConfigure()));

        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", jsPayReqData.getAppId());
        map.put("timeStamp", jsPayReqData.getTimeStamp());
        map.put("nonceStr", jsPayReqData.getNonceStr());
        map.put("package", jsPayReqData.getPackage_());
        map.put("signType", jsPayReqData.getSignType());
        map.put("paySign", jsPayReqData.getSign());
        map.put("orderPayNo", outTradeNo);
        return map;
    }

    protected String createPayHtml(Map<String, String> map) {
        return JsonUtil.toJson(map);
    }

    public void payNotifyAlipayReust(Map<String, String> requestParams, PayNotifyWeixinGZHCO recordPayDTO) {
        recordPayDTO.setNotifyReturnDTO(this.notifyReturnDTO);
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(requestParams);
        NotifyResData notifyResData = JsonUtil.hashMapToClass(map, NotifyResData.class);
        if ("FAIL".equals(notifyResData.getReturn_code())) {
            recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());
            return;
        }

        if (!Signature.checkIsSignValidFromResponseString(map, GZHConfigure.initMethod())) {
            throw new PayException(PayException.Enum.SIGNATURE_VALIDATION_FAILED_EXCEPTION);
        }
        recordPayDTO.setPayNo(notifyResData.getTransaction_id());
        recordPayDTO.setOutTradeNo(notifyResData.getOut_trade_no());
        recordPayDTO.setStatus(ERecordPayStatus.success.getCode());
        recordPayDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(notifyResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
    }

    /**
     * @param requestParams 返回参数 {"result":"微信返回参数","orderPayNo":"订单支付单号"}
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     */
    public void payResultAlipayReust(String requestParams, PaySyncResponseWeixinGZHCO recordPayDTO) {
        Map<String, Object> map = JsonUtil.jsonToHashMap(requestParams);
        if (map == null || StringUtils.isEmpty(map.get("orderPayNo"))) {
            throw new PayException(null, PayException.Enum.PAY_FAILED_EXCEPTION);
        }
        recordPayDTO.setOutTradeNo(map.get("orderPayNo").toString());
        recordPayDTO.setStatus(ERecordPayStatus.failed.getCode());

        if (!"get_brand_wcpay_request:ok".equals(map.get("result"))) {
            throw new PayException(null, PayException.Enum.PAY_FAILED_EXCEPTION);
        }

        OrderQueryResData orderQueryResData = weiXinPayQueryFacade.getOrderQuery(map.get("orderPayNo").toString(), GZHConfigure.initMethod());


        recordPayDTO.setPayNo(orderQueryResData.getTransaction_id());
        recordPayDTO.setOutTradeNo(orderQueryResData.getOut_trade_no());
        recordPayDTO.setStatus(ERecordPayStatus.success.getCode());
        recordPayDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(orderQueryResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
    }

    /**
     * 根据微信code获取openId
     *
     * @param weiXinAuthCode
     * @return
     */
    public String getWeChatOpenIdByCode(String weiXinAuthCode) {
        if (StringUtils.isEmpty(weiXinAuthCode)) {
            throw new PayException(null, PayException.Enum.WECHAT_CODE_NOT_NULL_EXCEPTION);
        }
        OAuth20ResData oAuth20ResData = weiXinOAuth20Facade.getOAuth20Data(weiXinAuthCode);
        String openId = oAuth20ResData.getOpenid();
        return openId;
    }
}
