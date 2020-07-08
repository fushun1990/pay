package com.fushun.pay.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2018年12月21日00时13分
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fushun.pay", "com.fushun.framework", "com.alibaba.cola"})
@EnableAsync //开启异步调用
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
