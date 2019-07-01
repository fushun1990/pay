package com.fushun.pay.thirdparty.sdk.weixinpay.config;

import com.tencent.common.GZHConfigure;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class WeiCinConfig {

    @Bean(initMethod = "initMethod")
    @ConfigurationProperties("pay.weixin.gzh")
    public GZHConfigure initGZHConfig(){
        GZHConfigure configure=GZHConfigure.initMethod();
        return configure;
    }
}
