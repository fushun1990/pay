package com.fushun.pay.infrastructure.config;

import com.alibaba.cola.boot.Bootstrap;
import com.fushun.framework.jpa.CustomerRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;

import java.util.ArrayList;
import java.util.List;

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
    public Bootstrap bootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        List<String> packagesToScan = new ArrayList<>();
        packagesToScan.add("com.fushun.pay");
        packagesToScan.add("com.alibaba.cola");
        bootstrap.setPackages(packagesToScan);
        return bootstrap;
    }
}