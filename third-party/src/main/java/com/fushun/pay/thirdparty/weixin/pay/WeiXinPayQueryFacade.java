package com.fushun.pay.thirdparty.weixin.pay;

import com.fushun.framework.util.exception.exception.BusinessException;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.thirdparty.weixin.pay.listener.AResultListener;
import com.tencent.WXPay;
import com.tencent.common.Configure;
import com.tencent.protocol.order_query_protocol.AppOrderQueryReqData;
import com.tencent.protocol.order_query_protocol.OrderQueryReqData;
import com.tencent.protocol.order_query_protocol.OrderQueryResData;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付订单状态查询
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月6日
 */
@Component
public class WeiXinPayQueryFacade {

    static Map<String, String> trade_stateMap = new HashMap<String, String>();

    static {
        trade_stateMap.put("SUCCESS", "支付成功");
        trade_stateMap.put("REFUND", "转入退款");
        trade_stateMap.put("NOTPAY", "未支付");
        trade_stateMap.put("CLOSED", "已关闭");
        trade_stateMap.put("REVOKED", "已撤销（刷卡支付");
        trade_stateMap.put("PAYERROR", "支付失败(其他原因，如银行返回失败");


    }

    private static void checkTradeStateMap(String tradeState) {
        if ("SUCCESS".equals(tradeState)) {
            return;
        }
        String errorMsg = trade_stateMap.get(tradeState);
        if (StringUtils.isEmpty(errorMsg)) {
            throw new PayException(PayException.Enum.PAY_FAILED_EXCEPTION);
        }
        throw new PayException(PayException.Enum.PAY_FAILED_EXCEPTION);
    }

    private OrderQueryReqData getReq(String payNo, Configure configure) {
        AppOrderQueryReqData orderQueryReqData = new AppOrderQueryReqData(configure);
        orderQueryReqData.setOut_trade_no(payNo);
        return orderQueryReqData;
    }

    public OrderQueryResData getOrderQuery(String payNo, Configure configure) {
        OrderQueryResultListener orderQueryResultListener = new OrderQueryResultListener();
        OrderQueryReqData orderQueryReqData = getReq(payNo, configure);
        try {
            WXPay.orderQueryBusiness(orderQueryReqData, orderQueryResultListener);
            OrderQueryResData orderQueryResData = orderQueryResultListener.getResData();
            checkTradeStateMap(orderQueryResData.getTrade_state());
            return orderQueryResData;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new PayException(e, PayException.Enum.QUERY_REQUEST_FAILED_EXCEPTION);
        }

    }

    private class OrderQueryResultListener extends AResultListener<OrderQueryResData> {

        private OrderQueryResData orderQueryResData = null;


        @Override
        public OrderQueryResData getResData() {
            return orderQueryResData;
        }


        @Override
        public void onSuccess(OrderQueryResData t) {
            this.orderQueryResData = t;

        }


    }
}
