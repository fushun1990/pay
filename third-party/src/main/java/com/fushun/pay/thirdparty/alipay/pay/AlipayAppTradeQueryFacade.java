package com.fushun.pay.thirdparty.alipay.pay;

import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fushun.framework.util.exception.exception.BusinessException;
import com.fushun.framework.util.util.EnumUtil;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.app.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.thirdparty.co.TradeQueryRequestDTO;
import com.fushun.pay.thirdparty.co.TradeQueryResponseCO;
import com.fushun.pay.thirdparty.sdk.alipay.enumeration.ETradeStatus;
import com.fushun.pay.thirdparty.sdk.alipay.util.AlipaySubmitApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * alipay 统一收单线下交易查询
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2016年9月18日
 */
@Component
public class AlipayAppTradeQueryFacade {

    private static Map<String, String> errorMap = new HashMap<String, String>();

    /**
     * 初始化接口错误
     */
    static {
        errorMap.put("ACQ.SYSTEM_ERROR", "系统错误	重新发起请求");
        errorMap.put("ACQ.INVALID_PARAMETER", "参数无效	检查请求参数，修改后重新发起请求");
        errorMap.put("ACQ.TRADE_NOT_EXIST", "查询的交易不存在	检查传入的交易号是否正确，修改后重新发起请求");

    }

    @Autowired
    private ValidatorServiceResponse validatorServiceResponse;

    public TradeQueryResponseCO tradeQuery(TradeQueryRequestDTO tradeQueryRequestDTO) {
        Map<String, String> map = getRequestData(tradeQueryRequestDTO);

        return refundRequest(map, tradeQueryRequestDTO);
    }

    /**
     * 获取查询对象
     *
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年9月14日
     * @records <p>  fushun 2016年9月14日</p>
     */
    private Map<String, String> getRequestData(TradeQueryRequestDTO tradeQueryRequestDTO) {
        // 把请求参数打包成数组

        Map<String, String> map = new HashMap<>();
        map.put("out_trade_no", tradeQueryRequestDTO.getOutTradeNo());

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
    private TradeQueryResponseCO refundRequest(Map<String, String> map, TradeQueryRequestDTO tradeQueryRequestDTO) {
        AlipayTradeQueryResponse response = null;
        try {
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数
            //此次只是参数展示，未进行字符串转义，实际情况下请转义
            request.setBizContent(JsonUtil.classToJson(map));
            response = AlipaySubmitApp.execute(request);
        } catch (Exception e) {
            throw new PayException(e, PayException.Enum.QUERY_REQUEST_FAILED_EXCEPTION);
        }

        TradeQueryResponseCO recordPayDTO = new TradeQueryResponseCO();

        //交易不存在
        if ("ACQ.TRADE_NOT_EXIST".equals(response.getSubCode())) {
            recordPayDTO.setOutTradeNo(tradeQueryRequestDTO.getOutTradeNo());
            recordPayDTO.setStatus(ERecordPayStatus.failed);
            recordPayDTO.setEPayFrom(tradeQueryRequestDTO.getEPayFrom());
            return recordPayDTO;
        }
        try {
            validatorServiceResponse.validatorServiceResponse(response, errorMap);
        } catch (BusinessException e) {
            //验证失败，修改支付状态是失败
            throw e;
        } catch (Exception e) {
            throw new PayException(e, PayException.Enum.VALIDATION_RETURN_UNDEFINED_EXCEPTION);
        }

        recordPayDTO.setOutTradeNo(response.getOutTradeNo());
        recordPayDTO.setPayNo(response.getTradeNo());

        //WAIT_BUYER_PAY（交易创建，等待买家付款）、
        //		TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
        //		TRADE_SUCCESS（交易支付成功）、
        //		TRADE_FINISHED（交易结束，不可退款）
        ETradeStatus tradeStatus = EnumUtil.getEnum(ETradeStatus.class, response.getTradeStatus());
        if (tradeStatus == null) {
            throw new PayException(null, PayException.Enum.ALIPAY_ORDER_STATUS_EXCEPTION);
        }
        switch (tradeStatus) {
            case WAIT_BUYER_PAY:
                recordPayDTO.setStatus(ERecordPayStatus.failed);
                break;
            case TRADE_CLOSED:
                recordPayDTO.setStatus(ERecordPayStatus.failed);
                break;
            case TRADE_SUCCESS:
                recordPayDTO.setStatus(ERecordPayStatus.success);
                break;
            case TRADE_FINISHED:
                recordPayDTO.setStatus(ERecordPayStatus.success);
                break;
            default:
                break;
        }
        recordPayDTO.setPayMoney(BigDecimal.valueOf(Double.valueOf(response.getTotalAmount())));
        return recordPayDTO;
    }

}
