package com.agefades.log.system.service.controller;

import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.agefades.log.common.core.base.Result;
import com.agefades.log.common.core.enums.HttpEnum;
import com.agefades.log.common.core.util.HttpUtil;
import com.agefades.log.common.core.util.dto.SysUserDTO;
import com.agefades.log.common.log.annotation.SysLog;
import com.agefades.log.order.client.TestClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api(tags = "测试API")
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestClient testClient;

    @GetMapping("update/metadata")
    @ApiOperation(value = "测试修改元数据")
    public Result<Void> testUpdateMetadata() {
        Map<String, Object> form = new HashMap<>();
        form.put("namespaceId", "public");
        form.put("serviceName", "log-order");
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("test", "agefades123");
        metadata.put("gg", "mm123");
        form.put("metadata", JSONUtil.toJsonStr(metadata));
        HttpUtil.doPut(HttpEnum.NACOS, "http://localhost:8848/nacos/v1/ns/instance/metadata/batch?" + URLUtil.buildQuery(form, null));
        return Result.success();
    }

    @GetMapping("feign")
    @ApiOperation(value = "测试 - 服务间调用")
    public Result<SysUserDTO> testFeign() {
        log.info("服务调用，输出日志测试");
        return testClient.testOk();
    }

    @SysLog(desc = "操作日志测试 - 正常")
    @GetMapping("ok")
    @ApiOperation(value = "测试 - 正常")
    public Result<Void> testOk() {
        return Result.success();
    }

    @SysLog(desc = "操作日志测试 - 异常")
    @PostMapping("fail")
    @ApiOperation(value = "测试 - 异常")
    public Result<Void> testError() {
        int[] arr = new int[]{1};
        System.out.println(arr[3]);
        return Result.success();
    }

    @PostMapping("fail/npe")
    @ApiOperation(value = "测试 - 空指针异常")
    public Result<Void> testErrorNpe() {
        SysUserDTO sysUserDTO = null;
        sysUserDTO.getId();
        return Result.success();
    }

    @PostMapping("fail/log")
    @ApiOperation(value = "测试 - 日志异常")
    public Result<Void> testErrorLog() {
        log.error("测试异常日志");
        return Result.success();
    }

    @PostMapping("fail/log/template")
    @ApiOperation(value = "测试 - 模板消息日志异常")
    public Result<Void> testErrorLogTemplate() {
        log.error("微信模板消息发送失败, 模板id[1], 错误原因:[测试日志]");
        return Result.success();
    }

}
