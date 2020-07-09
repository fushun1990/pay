package com.fushun.pay.client.dto.clientobject.notify;

import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时23分
 */
@Data
public class PayNotifyThirdPartyAlipayWapDTO extends PayNotifyThirdPartyDTO {

    /**
     * 买家邮箱
     */
    private String receiveAccourt;

}
