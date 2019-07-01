package com.fushun.pay.app.service;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.fushun.pay.app.api.OAuthServiceI;
import com.fushun.pay.app.dto.OAuth20Cmd;
import com.fushun.pay.app.dto.clientobject.oauth20.OAuth20ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OAuthServiceImpl implements OAuthServiceI {

    @Autowired
    private CommandBusI commandBus;

    @Override
    public SingleResponse<OAuth20ResponseVO> oahth20(OAuth20Cmd oAuth20Cmd) {
        SingleResponse<OAuth20ResponseVO> response = (SingleResponse<OAuth20ResponseVO>) commandBus.send(oAuth20Cmd);
        return response;
    }
}
