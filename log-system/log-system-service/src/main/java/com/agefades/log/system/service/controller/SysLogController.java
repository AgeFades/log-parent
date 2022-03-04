package com.agefades.log.system.service.controller;

import com.agefades.log.common.core.base.Result;
import com.agefades.log.common.datasource.base.PageResp;
import com.agefades.log.common.log.entity.SysLog;
import com.agefades.log.common.log.req.SysLogQueryReq;
import com.agefades.log.common.log.service.SysLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志API
 *
 * @author DuChao
 * @date 2020/9/29 2:05 下午
 */
@Api(tags = "操作日志API")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    SysLogService sysLogService;

    @ApiOperation(value = "操作日志多条件分页查询")
    @PostMapping("condition/page")
    public Result<PageResp<SysLog>> conditionPage(@RequestBody SysLogQueryReq req){
        Page<SysLog> page = sysLogService.conditionPage(req);
        return Result.success(new PageResp<>(page));
    }

    @ApiOperation(value = "测试privete")
    @GetMapping("private")
    private Result<PageResp<SysLog>> privateTest(){
        Page<SysLog> page = sysLogService.conditionPage(null);
        return Result.success(new PageResp<>(page));
    }

    @ApiOperation(value = "测试public")
    @GetMapping("public")
    public Result<PageResp<SysLog>> publicTest(){
        Page<SysLog> page = sysLogService.conditionPage(null);
        return Result.success(new PageResp<>(page));
    }

}

