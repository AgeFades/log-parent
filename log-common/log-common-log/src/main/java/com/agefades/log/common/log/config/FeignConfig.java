package com.agefades.log.common.log.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局Feign配置类
 *
 * @author DuChao
 * @date 2021/12/6 6:39 下午
 */
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
