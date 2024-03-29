package com.agefades.log.common.core.util;

import cn.hutool.core.convert.Convert;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程上下文工具类
 *
 * @author DuChao
 * @date 2020/9/2 10:42 上午
 */
public class ThreadLocalUtil {

    /**
     * 线程上下文 ThreadLocal
     * 以当前 线程 为 Key，Value 为 Map
     */
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL_MAP = ThreadLocal.withInitial(HashMap::new);

    /**
     * 往当前线程上下文插入 键值对
     *
     * @param key   键
     * @param value 值
     * @param <T>   值类型
     */
    public static <T> void set(String key, T value) {
        THREAD_LOCAL_MAP.get().put(key, value);
    }

    /**
     * 从当前线程上下文取值，不会抛出异常，转换失败 | 空值 都返回 null
     *
     * @param key    键
     * @param tClass 值类型Class
     * @param <T>    值类型
     * @return 值
     */
    public static <T> T get(String key, Class<T> tClass) {
        Object result = THREAD_LOCAL_MAP.get().get(key);
        return Convert.convertQuietly(tClass, result);
    }

    /**
     * 从当前线程上下文取值，不会抛出异常，转换失败 | 空值 使用入参默认值
     *
     * @param key          键
     * @param tClass       值类型Class
     * @param <T>          值类型
     * @param defaultValue 转换失败的默认值
     * @return 值
     */
    public static <T> T get(String key, Class<T> tClass, T defaultValue) {
        Object result = THREAD_LOCAL_MAP.get().get(key);
        return Convert.convertQuietly(tClass, result, defaultValue);
    }

    /**
     * 清空当前线程上下文
     */
    public static void reset() {
        THREAD_LOCAL_MAP.remove();
    }
}
