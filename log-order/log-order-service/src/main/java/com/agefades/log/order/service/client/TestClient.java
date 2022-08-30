package com.agefades.log.order.service.client;

import com.agefades.log.common.core.base.Result;
import com.agefades.log.common.core.util.UserInfoContextUtil;
import com.agefades.log.common.core.util.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Feign Client
 *
 * @author DuChao
 * @date 2021/12/6 6:13 下午
 */
@Slf4j
@RestController
@RequestMapping("/order/client")
public class TestClient {

    @GetMapping("ok")
    public Result<SysUserDTO> testOk() {
        log.info("服务调用，当前是灰度发布服务");
        return Result.success(UserInfoContextUtil.getSysUserDTO());
    }

}
