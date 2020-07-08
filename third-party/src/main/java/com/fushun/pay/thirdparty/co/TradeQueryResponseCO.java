package com.fushun.pay.thirdparty.co;

import com.fushun.pay.dto.enumeration.EPayFrom;
import com.fushun.pay.dto.enumeration.ERecordPayStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeQueryResponseCO {

    private String outTradeNo;
    private ERecordPayStatus status;
    private EPayFrom ePayFrom;
    private String payNo;
    private BigDecimal payMoney;
}
