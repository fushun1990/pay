package com.fushun.pay.app.dto.clientobject.createpay;

import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日00时32分
 */
@Data
public class CreatedPayRequestBodyCO {

    private String payStr;

    private EStatus status;
}
