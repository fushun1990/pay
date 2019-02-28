package com.fushun.pay.domain.refund.entity;

import com.alibaba.cola.domain.Entity;
import com.alibaba.cola.domain.EntityObject;
import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.framework.util.util.BeanUtils;
import com.fushun.pay.app.dto.enumeration.ERecordPayStatus;
import com.fushun.pay.app.dto.enumeration.ERefundFrom;
import com.fushun.pay.app.dto.enumeration.ERefundStatus;
import com.fushun.pay.domain.pay.repository.PayRepository;
import com.fushun.pay.domain.refund.exception.ErrorCode;
import com.fushun.pay.domain.refund.repository.RefundRepository;
import com.fushun.pay.infrastructure.pay.tunnel.database.dataobject.RecordPayDO;
import com.fushun.pay.infrastructure.refund.tunnel.database.dataobject.RefundDO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * @author wangfushun
 * @version 1.0
 * @description 退款
 * @creation 2019年02月03日23时48分
 */
@Entity
@Data
public class RefundE extends EntityObject {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 支付单号
     */
    private String tradeNo;

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 退款单号（第三方）
     */
    private String thirdRefundNo;

    /**
     * 退款金额
     */
    private BigDecimal refundMoney;

    /**
     * 退款源
     */
    private ERefundFrom eRefundFrom;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款状态
     */
    private ERefundStatus eRefundStatus;

    @Autowired
    private PayRepository payRepository;

    @Autowired
    private RefundRepository refundRepository;

    /**
     * @param
     * @return void
     * @description 退款
     * @date 2019年02月04日00时07分
     * @author wangfushun
     * @version 1.0
     */
    @Transactional
    public void refund() {
        String outTradeNo = this.eRefundFrom.getPreStr() + this.tradeNo;
        RecordPayDO recordPayDO = payRepository.findByOutTradeNO(outTradeNo);
        if (BeanUtils.isEmpty(recordPayDO)) {
            //订单不存在
            throw new BizException(ErrorCode.PAY_IS_NOT_EXIST, ErrorCode.PAY_IS_NOT_EXIST.getErrDesc());
        }
        RefundDO refundDO = refundRepository.findByRefundNoAndPayWay(this.refundNo, this.eRefundFrom);
        if (BeanUtils.isNotEmpty(refundDO)) {
            if (ERefundStatus.success.getCode().equals(refundDO.getStatus())) {
                //已退款
                throw new BizException(ErrorCode.REFUND_IS_SUCCESS, ErrorCode.REFUND_IS_SUCCESS.getErrDesc());
            }
            if (ERefundStatus.wait.getCode().equals(refundDO.getStatus())) {
                //正在退款
                throw new BizException(ErrorCode.REFUNDING, ErrorCode.REFUNDING.getErrDesc());
            }
        }

        //计算可退款金额
        BigDecimal returnableAmount = recordPayDO.getPayMoney().subtract(recordPayDO.getRefundAmount());
        recordPayDO.setRefundAmount(this.refundMoney.add(recordPayDO.getRefundAmount()));

        if (!ERecordPayStatus.success.getCode().equals(recordPayDO.getStatus())) {
            //支付未成功
            throw new BizException(ErrorCode.PAY_IS_NOT_SUCCESS, ErrorCode.PAY_IS_NOT_SUCCESS.getErrDesc());
        }

        if (returnableAmount.compareTo(BigDecimal.ZERO) == 0) {
            //订单金额为0
            throw new BizException(ErrorCode.PAY_MONEY_IS_ZERO, ErrorCode.PAY_MONEY_IS_ZERO.getErrDesc());
        }
        if (returnableAmount.compareTo(this.refundMoney) == -1) {
            //订单退款金额不足
            logger.info("pay available money:[{}],refund money:[{}]", recordPayDO.getPayMoney(), this.refundMoney);
            throw new BizException(ErrorCode.PAY_MONEY_IS_ZERO, ErrorCode.PAY_MONEY_IS_ZERO.getErrDesc() + "，可退金额" + recordPayDO.getPayMoney());
        }

        if (BeanUtils.isNotEmpty(refundDO)) {
            //退款存在，更状态
            logger.info("refund is exist,refundNo:[{}]", refundDO.getRefundNo());
            refundDO.setStatus(ERefundStatus.wait.getCode());
            refundRepository.update(refundDO);
            payRepository.update(recordPayDO);
            return;
        }

        //退款不存在，
        //保存退款信息
        refundRepository.create(this);
        payRepository.update(recordPayDO);
    }

    /**
     * @param
     * @return void
     * @description 退款失败
     * @date 2019年02月10日23时06分
     * @author wangfushun
     * @version 1.0
     */
    @Transactional
    public void fail() {

        String outTradeNo = this.eRefundFrom.getPreStr() + this.tradeNo;
        RecordPayDO recordPayDO = payRepository.findByOutTradeNO(outTradeNo);
        if (BeanUtils.isEmpty(recordPayDO)) {
            //订单不存在
            throw new BizException(ErrorCode.PAY_IS_NOT_EXIST, ErrorCode.PAY_IS_NOT_EXIST.getErrDesc());
        }
        RefundDO refundDO = refundRepository.findByRefundNoAndPayWay(this.refundNo, this.eRefundFrom);
        if (BeanUtils.isEmpty(refundDO)) {
            throw new BizException(ErrorCode.REFUND_IS_NOT_EXIST, ErrorCode.REFUND_IS_SUCCESS.getErrDesc());
        }
        if (ERefundStatus.success.getCode().equals(refundDO.getStatus())) {
            //已退款
            throw new BizException(ErrorCode.REFUND_IS_SUCCESS, ErrorCode.REFUND_IS_SUCCESS.getErrDesc());
        }

        //更新退款状态为失败
        refundDO.setStatus(this.eRefundStatus.getCode());
        refundRepository.update(refundDO);

        //还原已退款金额
        recordPayDO.setRefundAmount(recordPayDO.getRefundAmount().subtract(this.refundMoney));
        payRepository.update(recordPayDO);
    }

    /**
     * @param
     * @return void
     * @description 退款成功
     * @date 2019年02月10日23时26分
     * @author wangfushun
     * @version 1.0
     */
    public void success() {

        RefundDO refundDO = refundRepository.findByRefundNoAndPayWay(this.refundNo, this.eRefundFrom);
        if (BeanUtils.isEmpty(refundDO)) {
            throw new BizException(ErrorCode.REFUND_IS_NOT_EXIST, ErrorCode.REFUND_IS_SUCCESS.getErrDesc());
        }
        if (ERefundStatus.success.getCode().equals(refundDO.getStatus())) {
            //已退款
            throw new BizException(ErrorCode.REFUND_IS_SUCCESS, ErrorCode.REFUND_IS_SUCCESS.getErrDesc());
        }

        //更新退款成功
        refundDO.setStatus(this.eRefundStatus.getCode());
        refundDO.setThirdRefundNo(this.thirdRefundNo);
        refundRepository.update(refundDO);
    }
}
