package com.fushun.pay.app.dto.domainevent;

import com.alibaba.cola.dto.event.DomainEvent;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description 创建支付失败异常通知
 * @creation 2019年01月20日18时52分
 */
@Data
public class CreatedPayExceptionEvent extends DomainEvent {

    /**
     * 支付订单号
     */
    private String orderPayNo;

    /**
     * 外部订单号
     */
    private String outTradeNo;

}
