package com.agefades.log.common.core.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.agefades.log.common.core.constants.CommonConstant;
import org.slf4j.MDC;

import java.util.concurrent.Callable;

/**
 * 日志 traceId 工具类
 *
 * @author DuChao
 * @date 2021/1/12 3:21 下午
 */
public class LogUtil {

    /**
     * 获取 traceId
     *
     * @return traceId
     */
    public static String getTraceId() {
        return MDC.get(CommonConstant.TRACE_ID);
    }

    /**
     * 设置 traceId，方法内部生成
     */
    public static void setTraceId() {
        MDC.put(CommonConstant.TRACE_ID, IdUtil.fastSimpleUUID());
    }

    /**
     * 设置 traceId，传入 traceId，为空则方法自行生成
     *
     * @param traceId 需要设置的 traceId
     */
    public static void setTraceId(String traceId) {
        if (StrUtil.isBlank(traceId)) {
            traceId = IdUtil.fastSimpleUUID();
        }
        MDC.put(CommonConstant.TRACE_ID, traceId);
    }

    /**
     * 获取 userId
     *
     * @return traceId
     */
    public static String getUserId() {
        return MDC.get(CommonConstant.USER_ID);
    }

    /**
     * 设置 userId
     */
    public static void setUserId(String userId) {
        MDC.put(CommonConstant.USER_ID, userId);
    }

    /**
     * 清空 日志 线程上下文
     */
    public static void clearAll() {
        MDC.clear();
    }

    /**
     * 异步线程池 包装 任务
     *
     * @param callable 异步线程任务
     * @param traceId  父线程传入 traceId
     * @param userId   父线程传入 userId
     * @param <T>      泛型
     * @return 进行traceId处理后的 原任务调用结果
     */
    public static <T> Callable<T> wrap(final Callable<T> callable, String traceId, String userId) {
        return () -> {
            setTraceId(traceId);
            setUserId(userId);
            try {
                return callable.call();
            } finally {
                clearAll();
            }
        };
    }

    /**
     * 异步线程池 包装 任务
     *
     * @param runnable 异步线程任务
     * @param traceId  父线程传入 traceId
     * @param userId   父线程传入 userId
     * @return 进行traceId处理后的 原任务调用结果
     */
    public static Runnable wrap(final Runnable runnable, String traceId, String userId) {
        return () -> {
            setTraceId(traceId);
            setUserId(userId);
            try {
                runnable.run();
            } finally {
                clearAll();
            }
        };
    }

}
