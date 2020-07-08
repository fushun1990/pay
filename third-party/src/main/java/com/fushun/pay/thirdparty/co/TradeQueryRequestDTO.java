package com.fushun.pay.thirdparty.co;

import com.alibaba.cola.dto.ClientObject;
import com.fushun.pay.dto.enumeration.EPayFrom;
import lombok.Data;

@Data
public class TradeQueryRequestDTO extends ClientObject {

    private static final long serialVersionUID = 1L;

    private String outTradeNo;

    private EPayFrom ePayFrom;

}
