package com.agefades.log.common.core.util;

import com.agefades.log.common.core.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * 当前线程灰度标记上下文工具类
 *
 * @author DuChao
 * @date 2021/12/6 5:13 下午
 */
@Slf4j
public class GrayContextUtil extends ThreadLocalUtil {

    /**
     * 往当前线程ThreadLocal中存储灰度标记
     */
    public static void setGrayTag(Boolean tag) {
        set(CommonConstant.GRAY, tag);
    }

    /**
     * 从当前线程ThreadLocal中取出灰度标记，取不到默认为 false
     */
    public static Boolean getGrayTag() {
        return get(CommonConstant.GRAY, Boolean.class, Boolean.FALSE);
    }

}
