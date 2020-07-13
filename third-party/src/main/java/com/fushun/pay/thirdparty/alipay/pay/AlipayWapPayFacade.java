package com.fushun.pay.thirdparty.alipay.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.fushun.framework.util.beans.ConverterUtil;
import com.fushun.framework.util.util.DESUtil;
import com.fushun.framework.util.util.EnumUtil;
import com.fushun.framework.util.util.ExceptionUtils;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.client.config.PayConfig;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyAlipayWapDTO;
import com.fushun.pay.client.dto.clientobject.notify.PayNotifyThirdPartyDTO;
import com.fushun.pay.client.dto.clientobject.syncresponse.PaySyncResponseDTO;
import com.fushun.pay.dto.clientobject.NotifyReturnDTO;
import com.fushun.pay.dto.clientobject.createpay.CreatePayAlipayWapDTO;
import com.fushun.pay.dto.clientobject.createpay.enumeration.ECreatePayStatus;
import com.fushun.pay.dto.clientobject.createpay.response.CreatePayAliPayWapVO;
import com.fushun.pay.dto.clientobject.notify.PayNotifyAlipayWapDTO;
import com.fushun.pay.dto.clientobject.syncresponse.PaySyncResponseAlipayWapValidatorDTO;
import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.thirdparty.co.TradeQueryRequestDTO;
import com.fushun.pay.thirdparty.co.TradeQueryResponseCO;
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
 * 支付宝wap支付接口
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月7日
 */
@Component
public class AlipayWapPayFacade {

    /**
     * 返回对象
     */
    private static NotifyReturnDTO notifyReturnDTO;

    static {
        notifyReturnDTO = new NotifyReturnDTO();
        notifyReturnDTO.setFail("fail");
        notifyReturnDTO.setSuccess("success");
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlipayAppTradeQueryFacade alipayAppTradeQueryFacade;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private PayConfig payConfig;

    public CreatePayAliPayWapVO getRequest(CreatePayAlipayWapDTO payParamDTO) {
        //下单是不，更新订单为支付失败
        CreatePayAliPayWapVO createdPayThirdPartyCO = new CreatePayAliPayWapVO();
        createdPayThirdPartyCO.setStatus(ECreatePayStatus.SUCCESS);
        try {
            Map<String, String> map = this.getRequestData(payParamDTO);
            String payStr = this.createPayHtml(map);
            createdPayThirdPartyCO.setPayStr(payStr);
        } catch (Exception e) {
            createdPayThirdPartyCO.setStatus(ECreatePayStatus.FAIL);
            logger.warn("created pay error,payParamDTO:[{}]", payParamDTO.toString(), e);
        }
        return createdPayThirdPartyCO;
    }

    private Map<String, String> getRequestData(CreatePayAlipayWapDTO payParamDTO) {


        String outTradeNo = payParamDTO.getPayFrom().getPreStr() + payParamDTO.getTradeNo();

        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();

        if(payConfig.getStartWeb()){
            sParaTemp.put("notify_url", payConfig.getNotifyUrl());
        }else{
            sParaTemp.put("notify_url", payParamDTO.getNotifyUrl());
        }


        sParaTemp.put("return_url", payParamDTO.getReturnUrl());

        Map<String, Object> map = new HashMap<String, Object>();


//		body	String	否	128	对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。	Iphone6 16G
//		subject	String	是	256	商品的标题/交易标题/订单标题/订单关键字等。	大乐透
//		out_trade_no	String	是	64	商户网站唯一订单号	70501111111S001111119
//		timeout_express	String	否	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。	90m
//		total_amount	String	是	9	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]	9.00
//		seller_id	String	否	16	收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID	2088102147948060
//		product_code	String	是	64	销售产品码，商家和支付宝签约的产品码	QUICK_MSECURITY_PAY

        map.put("subject", payParamDTO.getSubject());
        map.put("out_trade_no", outTradeNo);
        map.put("seller_id", alipayConfig.getPartner());
        map.put("total_amount", payParamDTO.getTotalFee().toPlainString());
        map.put("product_code", "QUICK_MSECURITY_PAY");
        if (payParamDTO.getBody() != null) {
            map.put("body", payParamDTO.getBody());
        }
        sParaTemp.put("biz_content", JsonUtil.classToJson(map));

        return sParaTemp;


    }

    private String createPayHtml(Map<String, String> map) {
        AlipayClient client = new DefaultAlipayClient(alipayConfig.getGateway(), alipayConfig.getAppId(), alipayConfig.getRsaPrivateKeyPkcs8(), "json", alipayConfig.getInputCharset(), alipayConfig.getAliPayPublicKey(), alipayConfig.getSignType());
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(map.get("return_url"));
        alipayRequest.setNotifyUrl(map.get("notify_url"));//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent(map.get("biz_content"));//填充业务参数
        try {
            String form = client.pageExecute(alipayRequest, "GET").getBody();
            return form;
        } catch (AlipayApiException e) {
            throw new PayException(e, PayException.PayExceptionEnum.PAY_CREATE_FAILED);
        } //调用SDK生成表单
    }

    /**
     * 支付宝 wap 验证框架
     * @param payNotifyAlipayWapDTO
     * @return
     */
    public PayNotifyThirdPartyDTO payNotifyAlipayReust(PayNotifyAlipayWapDTO payNotifyAlipayWapDTO) {
//		参数	参数名称	类型	必填	描述	范例
//		notify_time	通知时间	Date	是	通知的发送时间。格式为yyyy-MM-dd HH:mm:ss	2015-14-27 15:45:58
//		notify_type	通知类型	String(64)	是	通知的类型	trade_status_sync
//		notify_id	通知校验ID	String(128)	是	通知校验ID	ac05099524730693a8b330c5ecf72da9786
//		app_id	开发者的app_id	String(32)	是	支付宝分配给开发者的应用Id	2014072300007148
//		charset	编码格式	String(10)	是	编码格式，如utf-8、gbk、gb2312等	utf-8
//		version	接口版本	String(3)	是	调用的接口版本，固定为：1.0	1.0
//		sign_type	签名类型	String(10)	是	签名算法类型，目前支持RSA	RSA
//		sign	签名	String(256)	是	请参考异步返回结果的验签	601510b7970e52cc63db0f44997cf70e
//		trade_no	支付宝交易号	String(64)	是	支付宝交易凭证号	2013112011001004330000121536
//		out_trade_no	商户订单号	String(64)	是	原支付请求的商户订单号	6823789339978248
//		out_biz_no	商户业务号	String(64)	否	商户业务ID，主要是退款通知中返回退款申请的流水号	HZRF001
//		buyer_id	买家支付宝用户号	String(16)	否	买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字	2088102122524333
//		buyer_logon_id	买家支付宝账号	String(100)	否	买家支付宝账号	15901825620
//		seller_id	卖家支付宝用户号	String(30)	否	卖家支付宝用户号	2088101106499364
//		seller_email	卖家支付宝账号	String(100)	否	卖家支付宝账号	zhuzhanghu@alitest.com
//		trade_status	交易状态	String(32)	否	交易目前所处的状态，见交易状态说明	TRADE_CLOSED
//		total_amount	订单金额	Number(9,2)	否	本次交易支付的订单金额，单位为人民币（元）	20
//		receipt_amount	实收金额	Number(9,2)	否	商家在交易中实际收到的款项，单位为元	15
//		invoice_amount	开票金额	Number(9,2)	否	用户在交易中支付的可开发票的金额	10.00
//		buyer_pay_amount	付款金额	Number(9,2)	否	用户在交易中支付的金额	13.88
//		point_amount	集分宝金额	Number(9,2)	否	使用集分宝支付的金额	12.00
//		refund_fee	总退款金额	Number(9,2)	否	退款通知中，返回总退款金额，单位为元，支持两位小数	2.58
//		subject	订单标题	String(256)	否	商品的标题/交易标题/订单标题/订单关键字等，是请求时对应的参数，原样通知回来	当面付交易
//		body	商品描述	String(400)	否	该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来	当面付交易内容
//		gmt_create	交易创建时间	Date	否	该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss	2015-04-27 15:45:57
//		gmt_payment	交易付款时间	Date	否	该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss	2015-04-27 15:45:57
//		gmt_refund	交易退款时间	Date	否	该笔交易的退款时间。格式为yyyy-MM-dd HH:mm:ss.S	2015-04-28 15:45:57.320
//		gmt_close	交易结束时间	Date	否	该笔交易结束时间。格式为yyyy-MM-dd HH:mm:ss	2015-04-29 15:45:57
//		fund_bill_list	支付金额信息	String(512)	否	支付成功的各个渠道金额信息，详见资金明细信息说明	[{“amount”:“15.00”,“fundChannel”:“ALIPAYACCOUNT”}]
//		passback_params	回传参数	String(512)	否	公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝	merchantBizType%3d3C%26merchantBizNo%3d2016010101111
//		voucher_detail_list	优惠券信息	String	否	本交易支付时所使用的所有优惠券信息，详见优惠券信息说明	[{“amount”:“0.20”,“merchantContribute”:“0.00”,“name”:“一键创建券模板的券名称”,“otherContribute”:“0.20”,“type”:“ALIPAY_DISCOUNT_VOUCHER”,“memo”:“学生卡8折优惠”]
        // 获取支付宝POST过来反馈信息
        PayNotifyThirdPartyAlipayWapDTO payNotifyThirdPartyAlipayWapDTO=new PayNotifyThirdPartyAlipayWapDTO();
        Map<String, String> requestParams=payNotifyAlipayWapDTO.getParamMap();

        payNotifyThirdPartyAlipayWapDTO.setEPayWay(EPayWay.PAY_WAY_ALIPAY);
        payNotifyThirdPartyAlipayWapDTO.setReceiveWay(EPayWay.PAY_WAY_ALIPAY);
        payNotifyThirdPartyAlipayWapDTO.setNotifyReturnDTO(AlipayWapPayFacade.notifyReturnDTO);
        Map<String, String> params = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            // String name = (String) iter.next();
            // String values = (String) requestParams.get(name);
            // String valueStr = "";
            // for (int i = 0; i < values.length; i++) {
            // valueStr = (i == values.length - 1) ? valueStr + values[i]
            // : valueStr + values[i] + ",";
            // }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(entry.getKey(), entry.getValue());
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号

        String out_trade_no = params.get("out_trade_no").toString();

        // 支付宝交易号
        String trade_no = params.get("trade_no").toString();

        // 交易状态
        String trade_status = params.get("trade_status").toString();

        String seller_email = params.get("seller_email").toString();
        float total_fee = Float.parseFloat(requestParams.get("total_amount").toString());

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

//        ;AlipayNotify.verify(params, alipayConfig.getInputCharset())

        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAliPayPublicKey(), alipayConfig.getInputCharset());
        } catch (Exception e) {
            ExceptionUtils.rethrow(new PayException(e, PayException.PayExceptionEnum.SIGNATURE_VALIDATION_FAILED),logger,"异步通知，签名异常，params:[{}]",JsonUtil.toJson(params));

        }
        if (!signVerified) {
            ExceptionUtils.rethrow(new PayException(PayException.PayExceptionEnum.SIGNATURE_VALIDATION_FAILED),logger,"异步通知，签名异常，params:[{}]",JsonUtil.toJson(params));
        }
        // 验证成功
        // ////////////////////////////////////////////////////////////////////////////////////////
        // 请在这里加上商户的业务逻辑程序代码

        payNotifyThirdPartyAlipayWapDTO.setOutTradeNo(out_trade_no);
        payNotifyThirdPartyAlipayWapDTO.setPayNo(trade_no);
        payNotifyThirdPartyAlipayWapDTO.setPayMoney(BigDecimal.valueOf(total_fee));
        try {
            payNotifyThirdPartyAlipayWapDTO.setReceiveAccourt(DESUtil.encrypt(seller_email, DESUtil.DES_KEY));
        } catch (Exception e) {
            logger.warn("支付宝支付，解密买家邮箱失败",e);
        }

        ETradeStatus tradeStatus = EnumUtil.getEnumByName(ETradeStatus.class, trade_status);
        if (tradeStatus == null) {
            ExceptionUtils.rethrow(new PayException(PayException.PayExceptionEnum.ALIPAY_ORDER_STATUS),logger,"支付宝，校验状态，不存在，trade_status:[{}]",trade_status);
        }
        switch (tradeStatus) {
            case WAIT_BUYER_PAY:
                payNotifyThirdPartyAlipayWapDTO.setStatus(ERecordPayStatus.FAILED);
                break;
            case TRADE_CLOSED:
                payNotifyThirdPartyAlipayWapDTO.setStatus(ERecordPayStatus.FAILED);
                break;
            case TRADE_SUCCESS:
                payNotifyThirdPartyAlipayWapDTO.setStatus(ERecordPayStatus.SUCCESS);
                break;
            case TRADE_FINISHED:
                payNotifyThirdPartyAlipayWapDTO.setStatus(ERecordPayStatus.SUCCESS);
                break;
            default:
                ExceptionUtils.rethrow(new PayException(PayException.PayExceptionEnum.ALIPAY_ORDER_STATUS),logger,"支付宝，校验状态，不存在，trade_status:[{}]",trade_status);
        }

        return payNotifyThirdPartyAlipayWapDTO;

    }


    public PaySyncResponseDTO payResultAlipayReust(PaySyncResponseAlipayWapValidatorDTO paySyncResponseAlipayWapValidatorDTO) {

        PaySyncResponseDTO paySyncResponseDTO=new PaySyncResponseDTO();
        Map<String, Object> requestParamsMap = JsonUtil.jsonToHashMap(paySyncResponseAlipayWapValidatorDTO.getResponseStr());

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        boolean signVerified = false;
        paySyncResponseDTO.setOutTradeNo(paySyncResponseAlipayWapValidatorDTO.getOutTradeNo());
        paySyncResponseDTO.setStatus(ERecordPayStatus.FAILED);

        try {
            Map<String, String> params = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : requestParamsMap.entrySet()) {
                params.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAliPayPublicKey(), alipayConfig.getInputCharset());
        } catch (AlipayApiException e) {
        }
        if (signVerified == false) {
            throw new PayException(null, PayException.PayExceptionEnum.SIGNATURE_VALIDATION_FAILED);
        }

        if (StringUtils.isEmpty(paySyncResponseAlipayWapValidatorDTO.getOutTradeNo())) {
            throw new PayException(null, PayException.PayExceptionEnum.PAY_RETURN_STATUS_ERROR);
        }

        TradeQueryRequestDTO tradeQueryRequestDTO = new TradeQueryRequestDTO();
        tradeQueryRequestDTO.setOutTradeNo(paySyncResponseAlipayWapValidatorDTO.getOutTradeNo());
        TradeQueryResponseCO tradeQueryResponseCO = alipayAppTradeQueryFacade.tradeQuery(tradeQueryRequestDTO);
        ConverterUtil.convert(tradeQueryResponseCO, paySyncResponseDTO);
        return paySyncResponseDTO;
    }

}
