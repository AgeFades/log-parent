package com.agefades.log.order.client;

import com.agefades.log.common.core.base.Result;
import com.agefades.log.common.core.constants.CommonConstant;
import com.agefades.log.common.core.util.dto.SysUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = CommonConstant.LOG_ORDER,
        path = "/order/client",
        fallback = TestClientFallback.class)
public interface TestClient {

    @GetMapping("ok")
    Result<SysUserDTO> testOk();

}
