package com.fushun.pay.app.dto.domainevent;

import com.alibaba.cola.dto.event.DomainEvent;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description 同步支付通知
 * @creation 2019年01月29日21时46分
 */
@Data
public class PaySyncResponseExceptionEvent extends DomainEvent {

    private String outTradeNo;
}
