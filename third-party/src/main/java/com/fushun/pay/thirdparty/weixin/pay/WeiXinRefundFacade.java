package com.fushun.pay.thirdparty.weixin.pay;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.EnumUtil;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.pay.app.dto.clientobject.refund.RefundWeixinCO;
import com.fushun.pay.app.dto.enumeration.EPayWay;
import com.fushun.pay.app.dto.enumeration.ERefundAccount;
import com.fushun.pay.domain.exception.PayException;
import com.fushun.pay.thirdparty.weixin.pay.exception.WeiXinRefundException;
import com.fushun.pay.thirdparty.weixin.pay.listener.AResultListener;
import com.tencent.WXPay;
import com.tencent.common.AppCConfigure;
import com.tencent.common.GZHConfigure;
import com.tencent.protocol.refund_protocol.RefundReqData;
import com.tencent.protocol.refund_protocol.RefundResData;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 微信退款 实现
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月6日
 */
@Component
public class WeiXinRefundFacade {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 微信退款
     *
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月7日
     */
    public String refundRequest(RefundWeixinCO refundWeixinCO) {
        RefundReqData refundReqData = getReq(refundWeixinCO);

        RefundResultListener resultListener = new RefundResultListener();

        try {
            WXPay.doRefundBusiness(refundReqData, resultListener);
            RefundResData refundResData = resultListener.getResData();
            logger.info("refund result:[{}]", JsonUtil.classToJson(refundResData));
            refundWeixinCO.setThirdRefundNo(refundResData.getRefund_id());

        } catch (WeiXinRefundException e) {
            logger.info("refund exception,change refundAccount,param:[{}]", JsonUtil.toJson(refundWeixinCO));
            //切换一种退款源方式，再次退款
            if (StringUtils.isEmpty(refundWeixinCO.getERefundAccount())) {
                refundWeixinCO.setERefundAccount(ERefundAccount.REFUND_SOURCE_RECHARGE_FUNDS);
                return refundRequest(refundWeixinCO);
            }
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("退款失败", e);
        }
        return null;
    }


    private RefundReqData getReq(RefundWeixinCO refundWeixinCO) {
        RefundReqData refundReqData;
        EPayWay ePayWay = EnumUtil.getEnum(EPayWay.class, refundWeixinCO.getEPayWay());
        switch (ePayWay) {
            case PAY_WAY_WEIXINPAY:
                refundReqData = new RefundReqData(null, GZHConfigure.initMethod());
                break;
            case PAY_WAY_APPC_WEIXINPAY:
                refundReqData = new RefundReqData(null, AppCConfigure.initMethod());
                break;
            default:
                throw new PayException(PayException.Enum.REFUND_ERROR_EXCEPTION);
        }
        ;
        refundReqData.setOut_trade_no(refundWeixinCO.getERefundFrom().getPreStr() + refundWeixinCO.getTradeNo());
        refundReqData.setOut_refund_no(refundWeixinCO.getERefundFrom().getPreStr() + refundWeixinCO.getRefundNo());
        refundReqData.setTotal_fee(refundWeixinCO.getRefundMoney().multiply(WeiXinUnifiedOrderFacade.bai).intValue());
        refundReqData.setRefund_fee(refundWeixinCO.getRefundMoney().multiply(WeiXinUnifiedOrderFacade.bai).intValue());

        if (!StringUtils.isEmpty(refundWeixinCO.getERefundAccount())) {
            refundReqData.setRefund_account(refundWeixinCO.getERefundAccount().getCode());
        }

        return refundReqData;
    }

    private class RefundResultListener extends AResultListener<RefundResData> {
        private RefundResData refundResData;

        @Override
        public void onFail(RefundResData refundResData) {
            if ("NOTENOUGH".equals(refundResData.getErr_code())) {
                throw new PayException(new Throwable(refundResData.getErr_code_des()), PayException.Enum.REFUND_ERROR_EXCEPTION);
            }
            this.onFail(refundResData);
        }

        @Override
        public void onSuccess(RefundResData t) {
            this.refundResData = t;
        }

        @Override
        public RefundResData getResData() {
            return this.refundResData;
        }

    }
}
