package com.agefades.log.order.client;

import com.agefades.log.common.core.base.Result;
import com.agefades.log.common.core.enums.CommonResultCodeEnum;
import com.agefades.log.common.core.util.dto.SysUserDTO;
import org.springframework.stereotype.Component;

@Component
public class TestClientFallback implements TestClient {
    @Override
    public Result<SysUserDTO> testOk() {
        return Result.error(CommonResultCodeEnum.SYSTEM_ERROR);
    }
}
