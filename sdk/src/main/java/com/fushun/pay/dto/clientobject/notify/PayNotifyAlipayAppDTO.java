package com.fushun.pay.dto.clientobject.notify;

import com.fushun.pay.dto.clientobject.PayNotifyDTO;
import lombok.Data;

import java.util.Map;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月22日00时23分
 */
@Data
public class PayNotifyAlipayAppDTO extends PayNotifyDTO {

    /**
     * 异步通知数据
     */
    private Map<String, String> paramMap;
}
