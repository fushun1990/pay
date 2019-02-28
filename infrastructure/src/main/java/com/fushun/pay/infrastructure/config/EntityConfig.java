package com.fushun.pay.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangfushun
 * @version 1.0
 * @description
 * @creation 2019年02月14日23时18分
 */
@Configuration
@EntityScan(basePackages = {"com.fushun.pay.infrastructure.*.tunnel.database.dataobject"})
public class EntityConfig {
}
