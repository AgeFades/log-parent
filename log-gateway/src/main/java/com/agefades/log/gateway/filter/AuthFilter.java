package com.agefades.log.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.agefades.log.common.core.constants.CommonConstant;
import com.agefades.log.common.core.constants.RedisConstant;
import com.agefades.log.common.core.enums.BoolEnum;
import com.agefades.log.common.core.enums.CommonResultCodeEnum;
import com.agefades.log.common.core.util.Assert;
import com.agefades.log.common.core.util.ExceptionUtil;
import com.agefades.log.common.core.util.JwtUtil;
import com.agefades.log.common.core.util.dto.CacheMenuDTO;
import com.agefades.log.common.core.util.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 认证鉴权过滤器
 *
 * @author DuChao
 * @date 2021/12/6 3:18 下午
 */
@Slf4j
@Component
@EnableConfigurationProperties(AuthIgnoreProperties.class)
public class AuthFilter implements GlobalFilter {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    AuthIgnoreProperties authIgnoreProperties;

    /**
     * Spring REST ful 风格路径匹配对象
     */
    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 请求路径
        String uri = request.getPath().value();
        // 请求协议
        HttpMethod method = request.getMethod();

        // 从请求头中token解析用户id，可能为空
        String token = JwtUtil.getJwtFromWebflux(request);
        String userId = JwtUtil.parseJwt(token);
        SysUserDTO sysUserDTO = null;
        List<CacheMenuDTO> permissions = null;

        // 用户id不为空时, 校验、延时、传输数据
        if (StrUtil.isNotBlank(userId)) {
            // 基础校验
            String hashKey = RedisConstant.SYS_USER_INFO_PREFIX + userId;
            Map<Object, Object> cacheMap = redisTemplate.boundHashOps(hashKey).entries();
            if (MapUtil.isEmpty(cacheMap)) {
                log.warn("token过期,token: {}, userId: {}", token, userId);
                ExceptionUtil.throwBizException(CommonResultCodeEnum.TOKEN_PARSE_ERROR);
            }

            if (ObjectUtil.notEqual(token, cacheMap.get(RedisConstant.SYS_USER_TOKEN))) {
                log.warn("账户在其他地方登录，token: {}, userId: {}", token, userId);
                ExceptionUtil.throwBizException(CommonResultCodeEnum.TOKEN_PARSE_ERROR, "账户已在其他地方登录,如非本人操作,请尽快修改密码");
            }

            Object userInfoObj = cacheMap.get(RedisConstant.SYS_USER_DTO);
            if (ObjectUtil.isNull(userInfoObj)) {
                log.warn("非法token，token: {}, userId: {}", token, userId);
                ExceptionUtil.throwBizException(CommonResultCodeEnum.TOKEN_PARSE_ERROR);
            }

            // token延时机制 -> 活跃用户token快过期时,延长其token时效
            Long expire = redisTemplate.getExpire(hashKey);
            if (expire != null && expire <= JwtUtil.REFRESH_SCORE_TTL) {
                redisTemplate.expire(hashKey, expire + JwtUtil.REFRESH_TTL, TimeUnit.SECONDS);
            }

            // 校验通过，传递用户数据
            String userInfoStr = userInfoObj.toString();
            sysUserDTO = JSONUtil.toBean(userInfoStr, SysUserDTO.class);
            permissions = JSONUtil.toList(JSONUtil.parseArray(cacheMap.get(RedisConstant.SYS_USER_PERMISSION)), CacheMenuDTO.class);
            exchange = exchange.mutate().request(request.mutate().header(CommonConstant.HEADER_SYS_USER, userInfoStr).build()).build();
        }

        // 判断是否为忽略认证鉴权的路径
        if (isIgnore(uri, method)) {
            // 忽略则直接放行
            return chain.filter(exchange);
        } else {
            // 需要认证鉴权的,断言认证
            Assert.notBlank(userId, CommonResultCodeEnum.TOKEN_PARSE_ERROR.getCode(), "当前操作需要登陆,请先完成登陆");
            Assert.notNull(sysUserDTO, CommonResultCodeEnum.TOKEN_PARSE_ERROR.getCode(), "当前操作需要登陆,请先完成登陆");
        }

        // 登陆认证
        Set<String> onlyLogin = authIgnoreProperties.getOnlyLogin();
        if (CollUtil.isNotEmpty(onlyLogin) && onlyLogin.stream().anyMatch(e -> MATCHER.match(e, uri))) {
            // 仅需登陆即可放行
            return chain.filter(exchange);
        }

        // 超管直接放行
        assert sysUserDTO != null;
        if (ObjectUtil.equal(BoolEnum.Y.getCode(), sysUserDTO.getIsAdmin())) {
            return chain.filter(exchange);
        }

        // 权限认证
        if (CollUtil.isNotEmpty(permissions) && permissions.stream().anyMatch(v -> MATCHER.match(v.getUri(), uri))) {
            return chain.filter(exchange);
        } else {
            // 认证失败
            log.warn("权限拦截, 用户id[{}]未拥有路径[{}]+协议[{}]的权限", userId, uri, method);
            ExceptionUtil.throwBizException(CommonResultCodeEnum.VALID_ERROR, "权限不足");
        }

        return null;
    }

    /**
     * 判断本次请求是否忽略认证鉴权
     *
     * @param uri    请求路径, 如: localhost:8080/user/add，则uri为 /user/add
     * @param method 请求协议 {@link HttpMethod}
     * @return 是否忽略
     */
    private boolean isIgnore(String uri, HttpMethod method) {
        // 基本配置判断
        if (CommonConstant.IGNORE_PATH.stream().anyMatch(uri::contains)) {
            return true;
        }

        // pattern判断
        Set<String> pattern = authIgnoreProperties.getPattern();
        if (CollUtil.isNotEmpty(pattern) && pattern.stream().anyMatch(uri::startsWith)) {
            return true;
        }

        // method + uri判断
        if (method == null) {
            method = HttpMethod.GET;
        }
        Set<String> uris = new HashSet<>();
        switch (method) {
            case GET:
                uris = authIgnoreProperties.getGet();
                break;
            case POST:
                uris = authIgnoreProperties.getPost();
                break;
            case PUT:
                uris = authIgnoreProperties.getPut();
                break;
            case DELETE:
                uris = authIgnoreProperties.getDelete();
                break;
            default:
                log.warn("该路径[{}]是不支持的请求协议: {}", uri, method);
        }

        return CollUtil.isNotEmpty(uris) && uris.stream().anyMatch(v -> MATCHER.match(v, uri));
    }

    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        // false
        System.out.println(matcher.match("/user/detail", "/user/detail/"));
        System.out.println(matcher.match("/user/**/do", "/user/do/xx"));
        System.out.println(matcher.match("", "/test/fail"));
        System.out.println(matcher.match("/user/*/detail", "/user/detail"));

        // true
        System.out.println(matcher.match("/user/**/detail", "/user/1/2/detail"));
        System.out.println(matcher.match("/user/detail", "/user/detail"));
        System.out.println(matcher.match("/user/detail/**", "/user/detail/1"));
        System.out.println(matcher.match("/user/**/do", "/user/xx/do"));
        System.out.println(matcher.match("/**/xx/do", "/user/xx/do"));
    }

}
