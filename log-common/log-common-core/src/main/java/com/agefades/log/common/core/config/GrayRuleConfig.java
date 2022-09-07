package com.agefades.log.common.core.config;

import org.springframework.context.annotation.Bean;

public class GrayRuleConfig {

    @Bean
    public GrayRule grayRule(){
        return new GrayRule();
    }

}
