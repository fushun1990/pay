package com.fushun.pay.app.interceptor;

import com.alibaba.cola.command.CommandInterceptorI;
import com.alibaba.cola.command.PreInterceptor;
import com.alibaba.cola.context.Context;
import com.alibaba.cola.dto.Command;
import com.fushun.pay.app.context.DemoContent;

@PreInterceptor
public class ContextInterceptor implements CommandInterceptorI {

    @Override
    public void preIntercept(Command command) {
        Context<DemoContent> context = new Context<DemoContent>();
        DemoContent content = new DemoContent();
        content.setUserId("testUserId");
        context.setContent(content);

        if (command.getContext() != null) {
            context.setBizCode(command.getContext().getBizCode());
        }

        command.setContext(context);

    }

}
