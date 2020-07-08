package com.fushun.pay.dto.clientobject.notify;

import com.fushun.pay.dto.clientobject.PayNotifyCO;
import com.fushun.pay.dto.enumeration.EPayWay;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时23分
 */
@Data
public class PayNotifyWeixinAppCO extends PayNotifyCO {

    /**
     * 买家邮箱
     */
    private String receiveAccourt;

    /**
     * 收款方式
     */
    private EPayWay receiveWay;

}
