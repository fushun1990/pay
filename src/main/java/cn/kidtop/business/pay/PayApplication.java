package cn.kidtop.business.pay;

import cn.kidtop.framework.jpa.CustomerRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;

/**
 * @author wangfushun
 * @version 1.0
 * @creation 2018年12月21日00时13分
 */
@EnableAutoConfiguration
@EntityScan(basePackages = {"cn.kidtop.business.pay.cmp"})
@SpringBootApplication
@ComponentScan(basePackages = {"cn.kidtop"})
@EnableJpaRepositories(
        repositoryBaseClass = CustomerRepositoryImpl.class,
        bootstrapMode = BootstrapMode.LAZY,
        basePackages = {"cn.kidtop.business.pay.repository"}
)
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class,args);
    }
}
