package com.agefades.log.gateway.exception;

import cn.hutool.json.JSONUtil;
import com.agefades.log.common.core.base.Result;
import com.agefades.log.common.core.enums.CommonResultCodeEnum;
import com.agefades.log.common.core.exception.BizException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关全局异常处理
 *
 * @author DuChao
 * @date 2021/12/6 2:37 下午
 */
@Slf4j
@Component
public class GatewayGlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        // 判断输出流状态
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        Result<Void> result;
        // 设置返回JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof BizException) {
            BizException bizException = (BizException) ex;
            result = Result.error(bizException.getCode(), bizException.getMessage());
        } else if (ex instanceof FeignException) {
            log.warn("服务调用异常: ", ex);
            result = Result.error(CommonResultCodeEnum.MICROSERVICE_INVOKE_ERROR);
        } else {
            log.error("网关系统异常: ", ex);
            result = Result.error(CommonResultCodeEnum.SYSTEM_ERROR);
        }

        Result<Void> finalResult = result;
        return response.writeWith(Mono.fromSupplier(() ->
        {
            DataBufferFactory bufferFactory = response.bufferFactory();
            // 输出JSON响应流
            return bufferFactory.wrap(JSONUtil.toJsonStr(finalResult).getBytes());
        }));

    }
}