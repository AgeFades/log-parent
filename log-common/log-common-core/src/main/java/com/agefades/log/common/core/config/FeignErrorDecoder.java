package com.agefades.log.common.core.config;

import com.agefades.log.common.core.enums.CommonResultCodeEnum;
import com.agefades.log.common.core.exception.BizException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * Feign异常处理, 找不到服务默认抛出 RuntimeException
 *
 * @author DuChao
 * @date 2021/12/24 10:28 AM
 */
@Slf4j
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        log.info("呵呵");
        return new BizException(CommonResultCodeEnum.MICROSERVICE_INVOKE_ERROR);
    }

}
