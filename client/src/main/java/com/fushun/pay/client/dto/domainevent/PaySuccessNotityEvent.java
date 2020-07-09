package com.fushun.pay.client.dto.domainevent;


import com.alibaba.cola.event.DomainEventI;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付成功 通知
 * @creation 2019年01月20日19时25分
 */
@Data
public class PaySuccessNotityEvent implements DomainEventI {
    /**
     * 支付订单号
     */
    private String orderPayNo;

    /**
     * 外部订单号
     */
    private String outTradeNo;
}
