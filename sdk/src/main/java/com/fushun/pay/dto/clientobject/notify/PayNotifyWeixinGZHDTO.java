package com.fushun.pay.dto.clientobject.notify;

import com.fushun.pay.dto.clientobject.PayNotifyDTO;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时23分
 */
@Data
public class PayNotifyWeixinGZHDTO extends PayNotifyDTO {

    /**
     * 异步通知字符串
     */
    private String notifyContent;
}
