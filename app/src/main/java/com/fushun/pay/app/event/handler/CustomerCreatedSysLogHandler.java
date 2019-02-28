package com.fushun.pay.app.event.handler;

import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.domainevent.CreatedPayExceptionEvent;

/**
 * 记录系统日志的handler
 *
 * @author Frank Zhang
 * @date 2019-01-04 11:24 AM
 */
@EventHandler
public class CustomerCreatedSysLogHandler implements EventHandlerI<CreatedPayExceptionEvent> {

    private Logger logger = LoggerFactory.getLogger(CustomerCreatedSysLogHandler.class);

    @Override
    public void execute(CreatedPayExceptionEvent event) {
        logger.debug("Logging system operation for new customer");
    }
}