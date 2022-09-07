package com.agefades.log.gateway;

import com.agefades.log.common.core.config.GrayRuleConfig;
import com.agefades.log.common.core.constants.CommonConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = CommonConstant.BASE_PACKAGES)
@RibbonClients(defaultConfiguration = GrayRuleConfig.class)
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
