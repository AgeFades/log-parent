package com.agefades.log.order.service.controller;

import com.agefades.log.common.core.base.Result;
import com.agefades.log.common.core.util.dto.SysUserDTO;
import com.agefades.log.common.log.annotation.SysLog;
import com.agefades.log.common.log.util.UserInfoContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "订单测试API")
@RestController
@RequestMapping("/order/test")
public class TestController {

    @GetMapping("ok")
    @ApiOperation(value = "测试 - 获取当前用户信息")
    public Result<SysUserDTO> testOk() {
        return Result.success(UserInfoContextUtil.getSysUserDTO());
    }

    @SysLog(desc = "订单操作日志测试 - 异常")
    @PostMapping("fail")
    @ApiOperation(value = "测试 - 异常")
    public Result<Void> testError() {
        int a = 1 / 0;
        return Result.success();
    }

    @PostMapping("fail/log")
    @ApiOperation(value = "测试 - 日志异常")
    public Result<Void> testErrorLog() {
        log.error("订单测试异常日志");
        return Result.success();
    }

}
