package com.agefades.log.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.agefades.log.common.core.base.CommonEnum;
import com.agefades.log.common.core.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举工具类
 *
 * @author DuChao
 * @date 2020/7/7 1:59 下午
 */
@Slf4j
public class EnumUtil {

    /**
     * 通过Code返回枚举
     *
     * @param code      : 枚举Code
     * @param enumClass : 枚举Class
     * @param <T>       : 枚举类型
     * @return 枚举对象
     */
    public static <T extends CodeEnum> T getByCode(String code, Class<T> enumClass) {
        if (StrUtil.isBlank(code)) return null;
        // 通过反射取出Enum所有常量的属性值
        for (T each : enumClass.getEnumConstants()) {
            // 利用code进行循环比较，获取对应的枚举
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 通过Msg返回枚举
     *
     * @param msg       : 枚举Msg
     * @param enumClass : 枚举Class
     * @param <T>       : 枚举类型
     * @return 枚举对象
     */
    public static <T extends CodeEnum> T getByMsg(String msg, Class<T> enumClass) {
        if (StrUtil.isBlank(msg)) return null;
        // 通过反射取出Enum所有常量的属性值
        for (T each : enumClass.getEnumConstants()) {
            // 利用code进行循环比较，获取对应的枚举
            if (msg.equals(each.getMsg())) {
                return each;
            }
        }
        return null;
    }

    /**
     * 通过Code获取Msg, 无对应枚举时原值返回
     */
    public static <T extends CodeEnum> String getMsgByCode(String code, Class<T> enumClass) {
        T enums = getByCode(code, enumClass);
        boolean flag = enums == null;
        if (flag) {
            log.error("出现错误枚举Code[{}],对应枚举类[{}]", code, enumClass.getName());
        }
        return flag ? code : enums.getMsg();
    }

    /**
     * 通过Msg获取Code, 无对应枚举时原值返回
     */
    public static <T extends CodeEnum> String getCodeByMsg(String msg, Class<T> enumClass) {
        T enums = getByMsg(msg, enumClass);
        boolean flag = enums == null;
        if (flag) {
            log.error("出现错误枚举Msg[{}],对应枚举类[{}]", msg, enumClass.getName());
        }
        return flag ? msg : enums.getCode();
    }

    /**
     * 通过Code、Msg枚举类获取对象集合
     */
    public static <T extends CodeEnum> List<CommonEnum> getEntities(Class<T> enumClass) {
        List<CommonEnum> entities = new ArrayList<>();
        for (T each : enumClass.getEnumConstants()) {
            entities.add(new CommonEnum(each.getCode(), each.getMsg()));
        }
        return entities;
    }

}
