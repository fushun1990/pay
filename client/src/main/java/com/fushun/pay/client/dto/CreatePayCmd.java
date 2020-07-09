package com.fushun.pay.client.dto;

import com.alibaba.cola.dto.Command;
import com.fushun.pay.dto.clientobject.PayDTO;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2019年01月18日23时22分
 */
public abstract class CreatePayCmd extends Command {

    public abstract PayDTO getPayDTO();
}
