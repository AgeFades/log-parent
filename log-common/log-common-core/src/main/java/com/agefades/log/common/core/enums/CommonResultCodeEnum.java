package com.agefades.log.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公共端业务响应Code、Msg枚举
 * 和前端统一code码标志,只使用4类code,
 * 1. 成功的code码200,
 * 2. 系统异常的code码500,
 * 3. 业务异常的code码400,(前端可直接将异常Msg用于客户端弹框)
 * 4. 需要特殊处理的code码以B开头加四位数字,(举例，B0001表示token登录态失效，需前端跳转至登录页指引用户重新登陆)
 * @author DuChao
 * @date 2021/1/12 2:44 下午
 */
@Getter
@AllArgsConstructor
public enum CommonResultCodeEnum implements CodeEnum {

    // ------ 基础code------//
    SUCCESS("200", "成功"),
    SYSTEM_ERROR("500", "系统异常,请联系管理员"),

    VALID_ERROR("400", "数据校验失败"),
    INVALID_JSON_ERROR("400", "请求JSON格式异常"),
    NOT_SUPPORTED_HTTP_MEDIA_TYPE("400", "不支持的HttpMediaType"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("400", "不支持的请求类型"),
    MAX_UPLOAD_SIZE_ERROR("400", "文件上传大小超过限制,请压缩文件或者降低图片质量再上传"),
    MISSING_REQUIRE_PARAMETER("400", "缺少必要参数"),
    METHOD_ARGUMENT_TYPE_MISMATCH("400", "参数值与参数类型不匹配"),
    MICROSERVICE_INVOKE_ERROR("400", "系统繁忙,请稍后再试"),
    ILLEGAL_ARGUMENT("400", "非法参数异常"),
    REDIS_LIMIT_ERROR("400", "操作过于频繁,请稍后再试"),
    JSON_PARSE_ERROR("400", "JSON 解析异常"),

    // ------ 特殊code ------//
    TOKEN_PARSE_ERROR("B0001", "当前登陆状态失效,请重新登陆"),
    ;

    /**
     * 响应 Code
     */
    private final String code;

    /**
     * 响应 Msg
     */
    private final String msg;
}
