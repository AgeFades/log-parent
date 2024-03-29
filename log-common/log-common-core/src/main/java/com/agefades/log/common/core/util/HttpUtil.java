package com.agefades.log.common.core.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.agefades.log.common.core.enums.HttpEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * 基于 Hutool 的一个 Http 客户端工具类
 */
@Slf4j
public class HttpUtil {

    /**
     * 默认超时时间 100000毫秒（100秒）
     */
    private static final int DEFAULT_TIME_OUT = 100000;

    /**
     * 发送 Http 请求，做日志记录
     *
     * @param httpEnum {@link HttpEnum}
     * @param method   {@link HttpMethod}
     * @param url      请求路径
     * @param header   请求头
     * @param form     请求表单
     * @param body     请求体
     * @return 请求结果
     */
    private static String doHttp(HttpEnum httpEnum, HttpMethod method, String url, Map<String, String> header, Map<String, Object> form, Object body) {
        String desc = httpEnum.getDesc();
        String bodyStr = JSONUtil.toJsonStr(body);
        log.info("[{}]请求路径: {}", desc, url);
        log.info("[{}]请求协议: {}", desc, method);
        if (MapUtil.isNotEmpty(header)) {
            log.info("[{}]请求头: {}", desc, JSONUtil.toJsonStr(header));
        }
        if (MapUtil.isNotEmpty(form)) {
            log.info("[{}]请求表单: {}", desc, JSONUtil.toJsonStr(form));
        }
        if (StrUtil.isNotBlank(bodyStr)) {
            log.info("[{}]请求体: {}", desc, bodyStr);
        }

        TimeInterval timer = DateUtil.timer();
        String result = "";

        if (method.equals(HttpMethod.POST)) {
            if (MapUtil.isNotEmpty(form)) {
                result = cn.hutool.http.HttpUtil.createPost(url)
                        .addHeaders(header)
                        .form(form)
                        .timeout(DEFAULT_TIME_OUT)
                        .execute()
                        .body();
            } else {
                result = cn.hutool.http.HttpUtil.createPost(url)
                        .addHeaders(header)
                        .body(bodyStr)
                        .timeout(DEFAULT_TIME_OUT)
                        .execute()
                        .body();
            }
        } else if (method.equals(HttpMethod.GET)) {
            result = cn.hutool.http.HttpUtil.createGet(url)
                    .addHeaders(header)
                    .form(form)
                    .timeout(DEFAULT_TIME_OUT)
                    .execute()
                    .body();
        } else if (method.equals(HttpMethod.PUT)) {
            if (MapUtil.isNotEmpty(form)) {
                result = cn.hutool.http.HttpUtil.createRequest(Method.PUT, url)
                        .addHeaders(header)
                        .form(form)
                        .timeout(DEFAULT_TIME_OUT)
                        .execute()
                        .body();
            } else {
                result = cn.hutool.http.HttpUtil.createRequest(Method.PUT, url)
                        .addHeaders(header)
                        .body(bodyStr)
                        .timeout(DEFAULT_TIME_OUT)
                        .execute()
                        .body();
            }
        }

        log.info("[{}]请求结果: {}", desc, result);
        log.info("[{}]请求耗时: {}", desc, timer.interval() + "毫秒");

        return result;
    }

    public static String doGet(HttpEnum httpEnum, String url) {
        return doHttp(httpEnum, HttpMethod.GET, url, null, null, null);
    }

    public static String doGet(HttpEnum httpEnum, String url, Map<String, Object> form) {
        return doHttp(httpEnum, HttpMethod.GET, url, null, form, null);
    }

    public static String doGet(HttpEnum httpEnum, String url, Map<String, String> header, Map<String, Object> form) {
        return doHttp(httpEnum, HttpMethod.GET, url, header, form, null);
    }

    public static <T> T doGet(HttpEnum httpEnum, String url, Class<T> clazz) {
        String result = doHttp(httpEnum, HttpMethod.GET, url, null, null, null);
        return JSONUtil.toBean(result, clazz);
    }

    public static <T> T doGet(HttpEnum httpEnum, String url, Map<String, Object> form, Class<T> clazz) {
        String result = doHttp(httpEnum, HttpMethod.GET, url, null, form, null);
        return JSONUtil.toBean(result, clazz);
    }

    public static <T> T doGet(HttpEnum httpEnum, String url, Map<String, String> header, Map<String, Object> form, Class<T> clazz) {
        String result = doHttp(httpEnum, HttpMethod.GET, url, header, form, null);
        return JSONUtil.toBean(result, clazz);
    }

    public static String doPost(HttpEnum httpEnum, String url, Map<String, String> header, Object body) {
        return doHttp(httpEnum, HttpMethod.POST, url, header, null, body);
    }

    public static String doPost(HttpEnum httpEnum, String url, Map<String, Object> form) {
        return doHttp(httpEnum, HttpMethod.POST, url, null, form, null);
    }

    public static String doPut(HttpEnum httpEnum, String url) {
        return doHttp(httpEnum, HttpMethod.PUT, url, null, null, null);
    }
    public static String doPut(HttpEnum httpEnum, String url, Map<String, Object> form) {
        return doHttp(httpEnum, HttpMethod.PUT, url, null, form, null);
    }

    public static String doPost(HttpEnum httpEnum, String url, Map<String, String> header, Map<String, Object> form, Object body) {
        return doHttp(httpEnum, HttpMethod.POST, url, header, form, body);
    }

    public static <T> T doPost(HttpEnum httpEnum, String url, Map<String, String> header, Object body, Class<T> clazz) {
        String result = doHttp(httpEnum, HttpMethod.POST, url, header, null, body);
        return JSONUtil.toBean(result, clazz);
    }

    public static <T> T doPost(HttpEnum httpEnum, String url, Map<String, Object> form, Class<T> clazz) {
        String result = doHttp(httpEnum, HttpMethod.POST, url, null, form, null);
        return JSONUtil.toBean(result, clazz);
    }

    public static <T> T doPost(HttpEnum httpEnum, String url, Map<String, String> header, Map<String, Object> form, Object body, Class<T> clazz) {
        String result = doHttp(httpEnum, HttpMethod.POST, url, header, form, body);
        return JSONUtil.toBean(result, clazz);
    }

}
