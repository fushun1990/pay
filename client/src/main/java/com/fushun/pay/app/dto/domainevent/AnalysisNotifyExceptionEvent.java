package com.fushun.pay.app.dto.domainevent;

import com.alibaba.cola.dto.event.DomainEvent;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月29日21时37分
 */
@Data
public class AnalysisNotifyExceptionEvent extends DomainEvent {

    private String outTradeNo;

}
