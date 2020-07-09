package com.fushun.pay.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import lombok.Data;

import java.util.Map;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时24分
 */
@Data
public class PayNotifyDTO extends ClientObject {

    /**
     * 异步通知数据
     */
    private Map<String, String> paramMap;

}
