package com.fushun.pay.start.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class StaticScheduleTask {

    private Logger logger= LoggerFactory.getLogger(StaticScheduleTask.class);

//    @Scheduled(cron="0/5 * * * * ?") //每5秒
    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1天
    public void task(){
        logger.info("开始定时任务");

        logger.info("定时任务结束");
    }

}
