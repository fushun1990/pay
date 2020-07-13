package com.fushun.pay.thirdparty.weixin.pay;

import com.fushun.framework.util.util.DateUtil;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.client.config.PayConfig;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyWeixinGZHDTO;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHDTO;
import com.fushun.pay.dto.clientobject.NotifyReturnDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayWeiXinGZHDTO;
import com.fushun.pay.dto.clientobject.createpay.enumeration.ECreatePayStatus;
import com.fushun.pay.dto.clientobject.createpay.response.CreatePayWeiXinGZHVO;
import com.fushun.pay.dto.clientobject.notify.PayNotifyWeixinGZHDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseWeixinGZHValidatorDTO;
import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.thirdparty.weixin.pay.exception.PayException;
import com.github.wxpay.sdk.WXPayUtil;
import com.tencent.common.GZHConfigure;
import com.tencent.common.Signature;
import com.tencent.protocol.jspay_protocol.JsPayReqData;
import com.tencent.protocol.jspay_protocol.NotifyResData;
import com.tencent.protocol.oauth20_protocol.OAuth20ResData;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
import com.tencent.protocol.userinfo_protocol.UserInfoResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private PayConfig payConfig;

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
    public CreatePayWeiXinGZHVO getRequest(CreatePayWeiXinGZHDTO payParamDTO) {
        //下单是不，更新订单为支付失败
        CreatePayWeiXinGZHVO createPayWeiXinGZHVO = new CreatePayWeiXinGZHVO();
        createPayWeiXinGZHVO.setStatus(ECreatePayStatus.SUCCESS);
        try {
            this.getRequestData(payParamDTO,createPayWeiXinGZHVO);
            return createPayWeiXinGZHVO;
        } catch (Exception e) {
            createPayWeiXinGZHVO.setStatus(ECreatePayStatus.FAIL);
            logger.warn("created pay error,payParamDTO:[{}]", payParamDTO.toString(), e);
        }
        return createPayWeiXinGZHVO;
    }

    /**
     * 组装支付参数
     * @param payParamDTO
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    protected void getRequestData(CreatePayWeiXinGZHDTO payParamDTO, CreatePayWeiXinGZHVO createPayWeiXinGZHVO) {
        String outTradeNo = payParamDTO.getPayFrom().getPreStr() + payParamDTO.getTradeNo();
        UnifiedorderResData unifiedorderResData = null;
        unifiedorderResData = weiXinUnifiedOrderFacade.unifiedOrderPay(payParamDTO);

        JsPayReqData jsPayReqData = new JsPayReqData(GZHConfigure.initMethod());
        jsPayReqData.setTimeStamp(String.valueOf(DateUtil.getTimestamp() / 1000));
        jsPayReqData.setPackage_("prepay_id=" + unifiedorderResData.getPrepay_id());
        jsPayReqData.setSign(Signature.getMD5Sign(Signature.getSignMap(JsPayReqData.class, jsPayReqData), jsPayReqData.getConfigure()));

        Map<String, String> map = new HashMap<String, String>();
        createPayWeiXinGZHVO.setAppId(jsPayReqData.getAppId());
        createPayWeiXinGZHVO.setTimestamp(jsPayReqData.getTimeStamp());
        createPayWeiXinGZHVO.setNonceStr(jsPayReqData.getNonceStr());
        createPayWeiXinGZHVO.setPackage_(jsPayReqData.getPackage_());
        createPayWeiXinGZHVO.setSignType(jsPayReqData.getSignType());
        createPayWeiXinGZHVO.setPaySign(jsPayReqData.getSign());
        createPayWeiXinGZHVO.setOrderPayNo(outTradeNo);

    }

    /**
     * 支付 异步通知，校验
     * @param payNotifyWeixinGZHDTO
     */
    public PayNotifyThirdPartyWeixinGZHDTO payNotifyAlipayReust(PayNotifyWeixinGZHDTO payNotifyWeixinGZHDTO) {
        PayNotifyThirdPartyWeixinGZHDTO payNotifyThirdPartyWeixinGZHDTO =new PayNotifyThirdPartyWeixinGZHDTO();
        payNotifyThirdPartyWeixinGZHDTO.setEPayWay(EPayWay.PAY_WAY_WEIXINPAY);
        payNotifyThirdPartyWeixinGZHDTO.setReceiveWay(EPayWay.PAY_WAY_WEIXINPAY);
        payNotifyThirdPartyWeixinGZHDTO.setNotifyReturnDTO(WeChatGZHPayFacade.notifyReturnDTO);
        Map<String, String> requestParams=null;
        try{
            // 转换成map
            requestParams = WXPayUtil.xmlToMap(payNotifyWeixinGZHDTO.getNotifyContent());
        }catch (Exception e){
            logger.error("异步同，转换失败。data:[{}]",payNotifyWeixinGZHDTO.getNotifyContent(),e);
            payNotifyThirdPartyWeixinGZHDTO.setStatus(ERecordPayStatus.EXCEPTION);
            return payNotifyThirdPartyWeixinGZHDTO;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(requestParams);
        NotifyResData notifyResData = JsonUtil.hashMapToClass(map, NotifyResData.class);
        if ("FAIL".equals(notifyResData.getReturn_code())) {
            payNotifyThirdPartyWeixinGZHDTO.setStatus(ERecordPayStatus.FAILED);
            return payNotifyThirdPartyWeixinGZHDTO;
        }

        if (!Signature.checkIsSignValidFromResponseString(map, GZHConfigure.initMethod())) {
            throw new PayException(PayException.PayExceptionEnum.SIGNATURE_VALIDATION_FAILED);
        }
        payNotifyThirdPartyWeixinGZHDTO.setPayNo(notifyResData.getTransaction_id());
        payNotifyThirdPartyWeixinGZHDTO.setOutTradeNo(notifyResData.getOut_trade_no());
        payNotifyThirdPartyWeixinGZHDTO.setStatus(ERecordPayStatus.SUCCESS);
        payNotifyThirdPartyWeixinGZHDTO.setPayMoney(BigDecimal.valueOf(Double.parseDouble(notifyResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));

        return payNotifyThirdPartyWeixinGZHDTO;
    }

    /**
     * 支付，同步校验
     * @param
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月6日
     */
    public PaySyncResponseDTO payResultAlipayReust(PaySyncResponseWeixinGZHValidatorDTO paySyncResponseWeixinGZHValidatorDTO) {
        PaySyncResponseWeixinGZHDTO paySyncResponseDTO=new PaySyncResponseWeixinGZHDTO();
        Map<String, Object> map = JsonUtil.jsonToHashMap(paySyncResponseWeixinGZHValidatorDTO.getResponseStr());
        if (map == null || StringUtils.isEmpty(paySyncResponseWeixinGZHValidatorDTO.getOutTradeNo())) {
            throw new PayException( PayException.PayExceptionEnum.PAY_FAILED);
        }
        paySyncResponseDTO.setOutTradeNo(paySyncResponseWeixinGZHValidatorDTO.getOutTradeNo());
        paySyncResponseDTO.setStatus(ERecordPayStatus.FAILED);

        if (!"get_brand_wcpay_request:ok".equals(map.get("result"))) {
            throw new PayException(PayException.PayExceptionEnum.PAY_FAILED);
        }

        OrderQueryResData orderQueryResData = weiXinPayQueryFacade.getOrderQuery(paySyncResponseWeixinGZHValidatorDTO.getOutTradeNo(), GZHConfigure.initMethod());


        paySyncResponseDTO.setPayNo(orderQueryResData.getTransaction_id());
        paySyncResponseDTO.setOutTradeNo(orderQueryResData.getOut_trade_no());
        paySyncResponseDTO.setStatus(ERecordPayStatus.SUCCESS);
        paySyncResponseDTO.setPayMoney(BigDecimal.valueOf(Double.parseDouble(orderQueryResData.getTotal_fee())).divide(WeiXinUnifiedOrderFacade.bai));
        paySyncResponseDTO.setReceiveWay(EPayWay.PAY_WAY_WEIXINPAY);

        return paySyncResponseDTO;
    }

    /**
     * 根据微信code获取 获取ACCESS_TOKEN 和 openId
     *
     * @param weiXinAuthCode
     * @return
     */
    public OAuth20ResData getAuthorizeByCode(String weiXinAuthCode) {
        if (StringUtils.isEmpty(weiXinAuthCode)) {
            throw new PayException(null, PayException.PayExceptionEnum.WECHAT_CODE_NOT_NULL);
        }
        OAuth20ResData oAuth20ResData = weiXinOAuth20Facade.getOAuth20Data(weiXinAuthCode);
        return oAuth20ResData;
    }

    /**
     * 获取用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    public UserInfoResData getUserInfoByOpenId(String accessToken, String openId){
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId)) {
            throw new PayException(null, PayException.PayExceptionEnum.WECHAT_CODE_NOT_NULL);
        }
        UserInfoResData userInfoResData = weiXinOAuth20Facade.getUserInfoByOpenId(accessToken,openId);
        return userInfoResData;
    }
}
