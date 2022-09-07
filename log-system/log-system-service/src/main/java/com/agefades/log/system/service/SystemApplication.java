package com.agefades.log.system.service;

import com.agefades.log.common.core.config.GrayRuleConfig;
import com.agefades.log.common.core.constants.CommonConstant;
import com.agefades.log.common.datasource.constants.DsConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(DsConstant.BASE_MAPPER_PACKAGE)
@ComponentScan(basePackages = CommonConstant.BASE_PACKAGES)
@EnableFeignClients(basePackages = CommonConstant.BASE_PACKAGES)
@RibbonClients(defaultConfiguration = GrayRuleConfig.class)
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
