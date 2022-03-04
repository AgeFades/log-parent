package com.agefades.log.system.service.controller;

import com.agefades.log.common.cache.annotation.RateLimiter;
import com.agefades.log.common.core.base.Result;
import com.agefades.log.common.datasource.base.PageResp;
import com.agefades.log.common.log.annotation.SysLog;
import com.agefades.log.system.common.req.SysUserAddReq;
import com.agefades.log.system.common.req.SysUserQueryReq;
import com.agefades.log.system.common.resp.SysUserQueryResp;
import com.agefades.log.system.service.service.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sys/user")
@Api(tags = "账户管理API")
@AllArgsConstructor
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @PostMapping("list")
    @ApiOperation(value = "条件分页查询")
    public Result<PageResp<SysUserQueryResp>> list(@RequestBody SysUserQueryReq req) {
        Page<SysUserQueryResp> page = sysUserService.conditionPage(req);
        return Result.success(new PageResp<>(page));
    }

    @RateLimiter
    @PostMapping("add")
    @SysLog(desc = "新增系统用户")
    @ApiOperation(value = "新增系统用户")
    public Result<Void> add(@RequestBody @Valid SysUserAddReq req) {
        sysUserService.add(req);
        return Result.success();
    }

}
