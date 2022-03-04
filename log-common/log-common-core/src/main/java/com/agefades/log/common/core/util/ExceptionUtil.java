package com.agefades.log.common.core.util;


import com.agefades.log.common.core.enums.CodeEnum;
import com.agefades.log.common.core.exception.BizException;

/**
 * 业务异常统一抛出工具类
 *
 * @author DuChao
 * @date 2021/1/12 3:44 下午
 */
public class ExceptionUtil {

    public static void throwBizException(CodeEnum codeEnum) {
        throwBizException(codeEnum.getCode(), codeEnum.getMsg());
    }

    public static void throwBizException(CodeEnum codeEnum, String message) {
        throwBizException(codeEnum.getCode(), message);
    }

    public static void throwBizException(String code, String message) {
        throw new BizException(code, message);
    }

}
