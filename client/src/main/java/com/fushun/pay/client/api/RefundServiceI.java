package com.fushun.pay.client.api;

import com.alibaba.cola.dto.Response;
import com.fushun.pay.client.dto.RefundCmd;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月15日22时02分
 */
public interface RefundServiceI {
    Response refund(RefundCmd refundCmd);
}
