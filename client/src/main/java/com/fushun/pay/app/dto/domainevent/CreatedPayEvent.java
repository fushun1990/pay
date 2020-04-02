package com.fushun.pay.app.dto.domainevent;

import com.alibaba.cola.event.DomainEventI;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月20日18时52分
 */
@Data
public class CreatedPayEvent implements DomainEventI {

    /**
     * 支付订单号
     */
    private String orderPayNo;

    /**
     * 外部订单号
     */
    private String outTradeNo;
}
