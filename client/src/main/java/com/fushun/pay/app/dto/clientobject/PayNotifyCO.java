package com.fushun.pay.app.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import com.fushun.pay.app.dto.enumeration.ERecordPayStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时24分
 */
@Data
public class PayNotifyCO extends ClientObject {

    private Map<String, String> paramMap;

    /**
     * 通知 必有参数字段
     */
    private String outTradeNo;

    private String payNo;

    private ERecordPayStatus status;

    private BigDecimal payMoney;

    /**
     * 异步通知 返回信息
     */
    private NotifyReturnDTO notifyReturnDTO;

}
