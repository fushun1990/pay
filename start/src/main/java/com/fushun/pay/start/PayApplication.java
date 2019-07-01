package com.fushun.pay.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2018年12月21日00时13分
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fushun.pay", "com.fushun.framework", "com.alibaba.cola"})
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
