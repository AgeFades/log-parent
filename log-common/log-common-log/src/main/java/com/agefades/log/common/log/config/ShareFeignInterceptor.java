package com.agefades.log.common.log.config;

import cn.hutool.extra.servlet.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Feign请求头传值拦截器
 *
 * @author DuChao
 * @date 2021/12/6 6:38 下午
 */
@Configuration
public class ShareFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            for (Map.Entry<String, String> entry : ServletUtil.getHeaderMap(request).entrySet()) {
                // 设置请求头到新的Request中
                template.header(entry.getKey(), entry.getValue());
            }
        }
    }

}
