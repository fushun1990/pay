package com.fushun.pay.app.event.handler;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.alibaba.cola.event.EventI;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.app.dto.domainevent.CreatedPayEvent;

import java.util.concurrent.ExecutorService;

/**
 * 同步搜索器的handler
 *
 * @author frankzhang
 * @date 2017/11/30
 */
@EventHandler
public class CustomerCreatedSyncSearchHandler implements EventHandlerI<Response,CreatedPayEvent> {

    private Logger logger = LoggerFactory.getLogger(CustomerCreatedSyncSearchHandler.class);

    @Override
    public Response execute(CreatedPayEvent event) {
        logger.debug("Sync new customer to Search");
        return null;
    }
}
