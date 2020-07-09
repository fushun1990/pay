package com.fushun.pay.client.dto.clientobject.notify;

import com.alibaba.cola.dto.ClientObject;
import com.fushun.pay.dto.clientobject.NotifyReturnDTO;
import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时24分
 */
@Data
public class PayNotifyThirdPartyDTO extends ClientObject {

    /**
     * 通知 必有参数字段
     */
    private String outTradeNo;

    private String payNo;

    private ERecordPayStatus status;

    private BigDecimal payMoney;

    /**
     * 支付方式
     */
    private EPayWay ePayWay;

    /**
     * 收款方式
     */
    private EPayWay receiveWay;

    /**
     * 异步通知 返回信息
     */
    private NotifyReturnDTO notifyReturnDTO;

}
