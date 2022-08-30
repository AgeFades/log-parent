package com.agefades.log.common.core.util;

import com.agefades.log.common.core.constants.CommonConstant;
import com.agefades.log.common.core.enums.CommonResultCodeEnum;
import com.agefades.log.common.core.util.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户信息上下文工具类
 *
 * @author DuChao
 * @date 2021/12/6 5:13 下午
 */
@Slf4j
public class UserInfoContextUtil extends ThreadLocalUtil {

    /**
     * 用户信息存储至当前应用线程上下文
     */
    public static void setSysUserDTO(SysUserDTO sysUserDTO) {
        set(CommonConstant.HEADER_SYS_USER, sysUserDTO);
    }

    /**
     * 获取当前应用线程上下文中的用户信息,获取失败则抛出异常,指引用户完成登陆
     */
    public static SysUserDTO getSysUserDTO() {
        return getSysUserDTO(false);
    }

    /**
     * 获取当前应用线程上下文中的用户信息
     *
     * @param isReturnNull : 获取不到时,是否返回空值,默认否
     */
    public static SysUserDTO getSysUserDTO(boolean isReturnNull) {
        SysUserDTO sysUserDTO = get(CommonConstant.HEADER_SYS_USER, SysUserDTO.class);
        if (sysUserDTO == null && isReturnNull) {
            return null;
        }
        Assert.notNull(sysUserDTO, CommonResultCodeEnum.TOKEN_PARSE_ERROR.getCode(), "当前操作需要登陆,请先完成登陆");
        return sysUserDTO;
    }

    /**
     * 获取当前用户名,获取失败则抛出异常,指引用户完成登陆
     */
    public static String getSysUserName() {
        return getSysUserDTO().getUsername();
    }

}
