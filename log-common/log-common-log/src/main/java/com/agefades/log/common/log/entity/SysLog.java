package com.agefades.log.common.log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志表
 *
 * @author DuChao
 * @date 2021/4/7 10:28 上午
 */
@Data
@Builder
@TableName("SYS_LOG")
@ApiModel("操作日志")
public class SysLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 操作用户
     */
    @ApiModelProperty("操作用户")
    private String username;
    /**
     * 操作结果, 成功:SUCCESS、失败:ERROR
     */
    @ApiModelProperty("操作结果, 成功:SUCCESS、失败:ERROR")
    private String resultType;
    /**
     * 操作资源类名
     */
    @ApiModelProperty("资源类")
    private String className;
    /**
     * 操作资源方法名
     */
    @ApiModelProperty("资源方法")
    private String methodName;
    /**
     * 请求类型, GET、POST、UPDATE、DELETE
     */
    @ApiModelProperty("请求类型, GET、POST、UPDATE、DELETE")
    private String methodType;
    /**
     * 日志描述
     */
    @ApiModelProperty("日志描述")
    private String logDesc;
    /**
     * 消耗时间，单位毫秒
     */
    @ApiModelProperty("耗时(毫秒)")
    private String elapsedTime;
    /**
     * 操作请求的ip
     */
    @ApiModelProperty("请求IP")
    private String ip;
    /**
     * 操作请求的参数
     */
    @JsonRawValue
    @ApiModelProperty("请求参数")
    private String params;
    /**
     * 异常Msg
     */
    @ApiModelProperty("异常信息")
    private String errorMsg;
    /**
     * 异常日志traceId
     */
    @ApiModelProperty("traceId")
    private String traceId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

}
