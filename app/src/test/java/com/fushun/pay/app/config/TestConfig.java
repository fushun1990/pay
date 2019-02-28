package com.fushun.pay.app.config;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.fushun.pay.infrastructure.config.ColaConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * TestConfig, use @Configuration to replace xml
 * <p>
 * COLA framework initialization is configured in {@link ColaConfig}
 *
 * @author Frank Zhang
 * @date 2018-08-08 12:33 PM
 */
@TestConfiguration
@ComponentScan(basePackages = {"com.fushun.pay", "com.alibaba.cola"})
@EnableAutoConfiguration
public class TestConfig {

    public TestConfig() {
        LoggerFactory.activateSysLogger();
        Logger logger = LoggerFactory.getLogger(TestConfig.class);
        logger.debug("Spring container is booting");
    }

}