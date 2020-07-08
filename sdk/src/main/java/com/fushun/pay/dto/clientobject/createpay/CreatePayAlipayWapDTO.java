package com.fushun.pay.dto.clientobject.createpay;

import com.fushun.pay.dto.clientobject.PayDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时30分
 */
@Data
public class CreatePayAlipayWapDTO extends PayDTO {
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

    /**
     * 支付返回地址
     */
    private String returnUrl;
}
