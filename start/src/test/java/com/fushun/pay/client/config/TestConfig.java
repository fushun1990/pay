package com.fushun.pay.client.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * TestConfig, use @Configuration to replace xml
 * <p>
 *
 * @author Frank Zhang
 * @date 2018-08-08 12:33 PM
 */
@TestConfiguration
@ComponentScan(basePackages = {"com.fushun.pay", "com.fushun.framework", "com.alibaba.cola"})
@EnableAutoConfiguration
public class TestConfig {

}