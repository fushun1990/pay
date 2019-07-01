package com.fushun.pay.app.dto;

import com.alibaba.cola.dto.Query;
import lombok.Data;

/**
 * @author wangfushun
 * @version 1.0
 * @description 支付查询
 * @creation 2019年01月19日23时05分
 */
@Data
public class PayFindByCriteriaQry extends Query {

    private String outTradeNo;
}
