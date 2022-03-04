package com.agefades.log.common.core.constants;

/**
 * Redis 缓存常量类
 *
 * @author DuChao
 * @date 2021/4/7 11:48 上午
 */
public interface RedisConstant {

    /**
     * 系统用户token过期时间(24小时)
     */
    Long SYS_USER_TOKEN_EXPIRE = 24L;

    /**
     * 系统用户token 记住登录 过期时间(72小时)
     */
    Long SYS_USER_TOKEN_REMEMBER_EXPIRE = 72L;

    /**
     * 系统用户权限
     */
    String SYS_USER_PERMISSION = "sys:user:permission";

    /**
     * 系统用户Token
     */
    String SYS_USER_TOKEN = "sys:user:token";

    /**
     * 系统用户信息Field
     */
    String SYS_USER_DTO = "sys:user:dto";

    /**
     * 系统用户信息前缀
     */
    String SYS_USER_INFO_PREFIX = "sys:user:info:";

}
