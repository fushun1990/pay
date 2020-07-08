package com.fushun.pay.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import com.fushun.pay.dto.enumeration.EPayWay;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时24分
 */
@Data
public class PaySyncResponseCO extends ClientObject {

    /**
     * 支付同步返回结果
     */
    private String responseStr;

    private String outTradeNo;

    private ERecordPayStatus status;

    /**
     * 支付方式
     */
    private EPayWay ePayWay;

    /**
     * 接收方式
     */
    private EPayWay receiveWay;

}
