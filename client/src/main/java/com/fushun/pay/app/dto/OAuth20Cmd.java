package com.fushun.pay.app.dto;

import com.alibaba.cola.dto.Command;
import com.fushun.pay.dto.clientobject.OAuth20CO;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年01月31日21时23分
 */
public abstract class OAuth20Cmd extends Command {

    public abstract OAuth20CO getOAuth20CO();

}
