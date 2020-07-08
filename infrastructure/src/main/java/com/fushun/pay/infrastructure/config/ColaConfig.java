package com.fushun.pay.infrastructure.config;

import com.alibaba.cola.boot.SpringBootstrap;
import com.fushun.framework.jpa.CustomerRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;

/**
 * Configuration for COLA framework
 */
@Configuration
@EnableJpaRepositories(
        repositoryBaseClass = CustomerRepositoryImpl.class,
        bootstrapMode = BootstrapMode.DEFAULT,
        basePackages = {"com.fushun.pay.infrastructure.*.tunnel"}
)
public class ColaConfig {

    @Bean(initMethod = "init")
    public SpringBootstrap bootstrap() {
        SpringBootstrap bootstrap = new SpringBootstrap();
        return bootstrap;
    }
}