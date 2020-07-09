package com.fushun.pay.client.dto.domainevent;

import com.alibaba.cola.event.DomainEventI;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description 创建支付失败异常通知
 * @creation 2019年01月20日18时52分
 */
@Data
public class CreatedPayExceptionEvent implements DomainEventI {

    /**
     * 支付订单号
     */
    private String orderPayNo;

    /**
     * 外部订单号
     */
    private String outTradeNo;

}
