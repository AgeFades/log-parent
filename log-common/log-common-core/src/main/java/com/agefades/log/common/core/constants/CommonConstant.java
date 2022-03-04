package com.agefades.log.common.core.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 公共常量类
 *
 * @author DuChao
 * @date 2021/1/12 4:52 下午
 */
public interface CommonConstant {

    /**
     * 请求日志TraceId
     */
    String TRACE_ID = "traceId";

    /**
     * 请求日志用户id
     */
    String USER_ID = "userId";

    /**
     * 项目名
     */
    String PROJECT_NAME = "log";

    /**
     * 系统服务
     */
    String LOG_SYSTEM = "log-system";

    /**
     * 订单服务
     */
    String LOG_ORDER = "log-order";

    /**
     * Spring IOC基础扫描包
     */
    String BASE_PACKAGES = "com.agefades.log.*";

    /**
     * 手机号校验正则
     */
    String PHONE_PATTEN = "1\\d{10}";

    /**
     * 不需要登录、鉴权即可访问的路径
     * 此处基本配置 各个整合框架 路径
     */
    List<String> IGNORE_PATH = Arrays.asList(
            ".html",
            ".css",
            ".js",
            "csrf",
            "api-docs",
            "swagger",
            "webjars",
            "error",
            "actuator",
            "null",
            "druid"
    );

    /**
     * 服务转发调用间, 存储用户信息的请求头Key
     */
    String HEADER_SYS_USER = "SYS_USER";

}
