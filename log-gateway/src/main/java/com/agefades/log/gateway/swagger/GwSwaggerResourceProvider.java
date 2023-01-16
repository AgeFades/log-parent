package com.agefades.log.gateway.swagger;

import lombok.AllArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 获取具体服务Swagger Api Docs
 *
 * @author DuChao
 * @date 2021/12/6 2:11 下午
 */
@Component
@Primary
@AllArgsConstructor
public class GwSwaggerResourceProvider implements SwaggerResourcesProvider {

    /**
     * swagger2默认的url后缀
     */
    private static final String SWAGGER2URL = "/v2/api-docs";

    /**
     * 用于获取各服务名称
     */
    private DiscoveryClient discoveryClient;

    @Override
    public List<SwaggerResource> get() {
        List<String> services = discoveryClient.getServices();
        List<SwaggerResource> resources = new ArrayList<>();
        // 记录已经添加过的server，存在同一个应用注册了多个服务在nacos上
        Set<String> already = new HashSet<>();
        services.forEach(v -> {
            // 拼接url，样式为/serviceId/v2/api-info，当网关调用这个接口时，会自动通过负载均衡寻找对应的主机
            String url = "/" + v + SWAGGER2URL;
            if (!already.contains(url)) {
                already.add(url);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(v);
                resources.add(swaggerResource);
            }
        });
        return resources;
    }
}