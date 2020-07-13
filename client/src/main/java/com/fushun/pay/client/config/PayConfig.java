package com.fushun.pay.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pay")
@Data
public class PayConfig {

    /**
     * 是否独立运行支付项目
     */
    private Boolean startWeb=false;

    /**
     * 独立运行支付项目，通知url地址
     * 需要其他项目配合，做对外请求的地址，然后通过方向代理请求到异步通知接口
     */
    private String notifyUrl;
}
