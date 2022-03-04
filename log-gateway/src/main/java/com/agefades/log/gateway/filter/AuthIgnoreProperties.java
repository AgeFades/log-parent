package com.agefades.log.gateway.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * 认证鉴权忽略配置属性类
 *
 * @author DuChao
 * @date 2021/12/6 3:21 下午
 */
@Data
@ConfigurationProperties(prefix = "auth.ignore")
public class AuthIgnoreProperties {

    /**
     * 忽略认证鉴权的get请求
     */
    private Set<String> get;

    /**
     * 忽略认证鉴权的post请求
     */
    private Set<String> post;

    /**
     * 忽略认证鉴权的put请求
     */
    private Set<String> put;

    /**
     * 忽略认证鉴权的delete请求
     */
    private Set<String> delete;

    /**
     * 忽略认证鉴权的请求,忽略以 xxx 开头的路径,不比对请求协议
     * 举例：/test，即忽略以 /test 开头的请求
     */
    private Set<String> pattern;

    /**
     * 仅需登陆,忽略鉴权的请求,不比对请求协议
     * 举例：/onlyLogin，即 /onlyLogin 请求需要验证是否登陆，无需验证是否拥有权限
     */
    private Set<String> onlyLogin;

}
