package com.fushun.pay.app.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import com.fushun.pay.app.dto.enumeration.EPayFrom;
import com.fushun.pay.app.dto.enumeration.EPayWay;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时24分
 */
@Data
public class PayCO extends ClientObject {

    /**
     * 支付方式
     */
    @NotNull
//(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
    private EPayWay payWay; //支付宝，网银，银联等
    /**
     * 支付源
     */
    @NotNull
//(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
    private EPayFrom payFrom; //web端，App端
    /**
     * 通知连接
     */
    @NotEmpty
//(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
    private String notifyUrl;
    /**
     * 支付单号
     */
    @NotEmpty
//(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
    private String tradeNo;

    /**
     * 支付金额
     * 保留两位小数
     */
    @NotNull
//(groups={CreateAlipayAppPayResultValidatorGroup.class,CreateWeiXinJZHPayValidatorGroup.class,CreateAlipayWapResultValidatorGroup.class,CreateWeiXinAppPayValidatorGroup.class,CreateAlipayDirectPayResultValidatorGroup.class})
    private BigDecimal totalFee;

    private Integer status;
}
