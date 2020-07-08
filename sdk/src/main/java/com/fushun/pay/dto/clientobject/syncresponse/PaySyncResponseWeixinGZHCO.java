package com.fushun.pay.dto.clientobject.syncresponse;

import com.fushun.pay.dto.clientobject.PaySyncResponseCO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月24日01时08分
 */
@Data
public class PaySyncResponseWeixinGZHCO extends PaySyncResponseCO {

    private String payNo;

    private BigDecimal payMoney;
}
