package com.agefades.log.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 三方Http枚举, 例:
 *      和淘宝对接, TAOBAO("淘宝")
 *
 * @author DuChao
 * @date 2021/1/12 4:30 下午
 */
@Getter
@AllArgsConstructor
public enum HttpEnum {

    TENCENT_MAP("腾讯地图"),
    NACOS("nacos"),
    ;
    private final String desc;

}
