package com.fushun.pay.thirdparty.alipay.refund;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.dto.clientobject.refund.RefundAlipayCO;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.thirdparty.alipay.pay.ValidatorServiceResponse;
import com.fushun.pay.thirdparty.sdk.alipay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 支付宝 app支付  退款 接口
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2016年9月10日
 */
@Component
public class AlipayAppRefundFacade {

    private static Map<String, String> errorMap = new HashMap<String, String>();

    /**
     * 初始化接口错误
     */
    static {
        errorMap.put("ACQ.SYSTEM_ERROR", "系统错误	请使用相同的参数再次调用");
        errorMap.put("ACQ.INVALID_PARAMETER", "参数无效	请求参数有错，重新检查请求后，再调用退款");
        errorMap.put("ACQ.SELLER_BALANCE_NOT_ENOUGH", "卖家余额不足	商户支付宝账户充值后重新发起退款即可");
        errorMap.put("ACQ.REFUND_AMT_NOT_EQUAL_TOTAL", "退款金额超限	检查退款金额是否正确，重新修改请求后，重新发起退款");
        errorMap.put("ACQ.REASON_TRADE_BEEN_FREEZEN", "请求退款的交易被冻结	联系支付宝小二，确认该笔交易的具体情况");
        errorMap.put("ACQ.TRADE_NOT_EXIST", "交易不存在	检查请求中的交易号和商户订单号是否正确，确认后重新发起");
        errorMap.put("ACQ.TRADE_HAS_FINISHED", "交易已完结	该交易已完结，不允许进行退款，确认请求的退款的交易信息是否正确");
        errorMap.put("ACQ.TRADE_STATUS_ERROR", "交易状态非法	查询交易，确认交易是否已经付款");
        errorMap.put("ACQ.DISCORDANT_REPEAT_REQUEST", "不一致的请求	检查该退款号是否已退过款或更换退款号重新发起请求");
        errorMap.put("ACQ.REASON_TRADE_REFUND_FEE_ERR", "退款金额无效	检查退款请求的金额是否正确");
        errorMap.put("ACQ.TRADE_NOT_ALLOW_REFUND", "当前交易不允许退款	检查当前交易的状态是否为交易成功状态以及签约的退款属性是否允许退款，确认后，重新发起请求");
    }

    @Autowired
    private ValidatorServiceResponse validatorServiceResponse;
    @Autowired
    private AlipayConfig alipayConfig;

    /**
     * 退款
     *
     * @param refundAlipayCO
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月14日
     * @records <p>  fushun 2016年9月14日</p>
     */
    public String refundRequest(RefundAlipayCO refundAlipayCO) {

//		recordRefundDTO.setRefundNo(refundParamDTO.getRefundNo());
//		recordRefundDTO.setPayWay(recordRefundDTO.getPayWay());

        Map<String, String> map = getRequestData(refundAlipayCO);

        refundRequest(map, refundAlipayCO);

        return null;
    }

    /**
     * 获取退款 参数对象
     *
     * @param refundAlipayCO
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月14日
     * @records <p>  fushun 2016年9月14日</p>
     */
    private Map<String, String> getRequestData(RefundAlipayCO refundAlipayCO) {

        Map<String, String> map = new HashMap<>();
        String outTradeNo="";
        if(!refundAlipayCO.getIsSpecial()){
         outTradeNo=refundAlipayCO.getERefundFrom().getEPayFrom().getPreStr();
        }
        outTradeNo+=refundAlipayCO.getTradeNo();
        map.put("out_trade_no",outTradeNo);
        map.put("refund_amount", String.valueOf(refundAlipayCO.getRefundMoney()));
        if (refundAlipayCO.getRefundReason() != null) {
            map.put("refund_reason", refundAlipayCO.getRefundReason());
        }
        map.put("out_request_no", refundAlipayCO.getERefundFrom().getEPayFrom().getPreStr() + refundAlipayCO.getRefundNo());

        return map;
    }

    /**
     * 调用支付 接口直接退款，
     *
     * @param map
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月13日
     * @records <p>  fushun 2016年9月13日</p>
     */
    private void refundRequest(Map<String, String> map, RefundAlipayCO refundAlipayCO) {

        AlipayTradeRefundResponse response = payRecordRefundAliPay(map);
        refundAlipayCO.setThirdRefundNo(response.getTradeNo());

        validatorServiceResponse.validatorServiceResponse(response, errorMap);

    }

    private AlipayTradeRefundResponse payRecordRefundAliPay(Map<String, String> map) {
        AlipayTradeRefundResponse response = null;
        try {
            //实例化客户端
            AlipayClient client = new DefaultAlipayClient(alipayConfig.getGateway(), alipayConfig.getAppId(), alipayConfig.getRsaPrivateKeyPkcs8(), "json", alipayConfig.getInputCharset(), alipayConfig.getAliPayPublicKey(), alipayConfig.getSignType());
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数
            //此次只是参数展示，未进行字符串转义，实际情况下请转义
            request.setBizContent(JsonUtil.classToJson(map));
            response = client.execute(request);
            //调用成功，则处理业务逻辑
        } catch (Exception e) {
            throw new PayException(e, PayException.PayExceptionEnum.REFUND_REQUEST_FAILED);
        }

        return response;
    }

}
