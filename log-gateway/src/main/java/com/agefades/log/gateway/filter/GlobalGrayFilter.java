package com.agefades.log.gateway.filter;

import cn.hutool.core.convert.Convert;
import com.agefades.log.common.core.constants.CommonConstant;
import com.agefades.log.common.core.util.GrayContextUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.reactive.ServerWebExchangeContextFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 灰度标记过滤器类, ServerWebExchangeContextFilter 才能在Ribbon自定义负载策略类前执行
 *
 * @author DuChao
 * @date 2022/8/3 14:31
 */
@Component
public class GlobalGrayFilter extends ServerWebExchangeContextFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Boolean grayTag = Boolean.FALSE;
        if (headers.containsKey(CommonConstant.GRAY)){
            grayTag = Convert.toBool(headers.getFirst(CommonConstant.GRAY));
        }
        // 将灰度标记放入当前线程上下文
        GrayContextUtil.setGrayTag(grayTag);
        // 将灰度标记放入请求头中
        ServerHttpRequest tokenRequest = exchange.getRequest().mutate()
                .header(CommonConstant.GRAY, Convert.toStr(grayTag))
                .build();
        ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
        return chain.filter(build);
    }

}