package com.agefades.log.common.log.config;

import cn.hutool.json.JSONUtil;
import com.agefades.log.common.core.constants.CommonConstant;
import com.agefades.log.common.core.util.LogUtil;
import com.agefades.log.common.log.util.UserInfoContextUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Feign请求头传值拦截器
 *
 * @author DuChao
 * @date 2021/12/6 6:38 下午
 */
@Configuration
public class ShareFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(CommonConstant.TRACE_ID, LogUtil.getTraceId());
        template.header(CommonConstant.HEADER_SYS_USER, JSONUtil.toJsonStr(UserInfoContextUtil.getSysUserDTO()));
    }

}
