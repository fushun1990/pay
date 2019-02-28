package com.fushun.pay.app.dto.clientobject.createpay;

import com.fushun.pay.app.dto.clientobject.PayCO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时30分
 */
@Data
public class CreatePayAlipayAppCO extends PayCO {
    /**
     * 支付 标题
     */
    @NotEmpty
//(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
    private String subject;

    /**
     * 支付信息描述
     */
    private String body;
}
