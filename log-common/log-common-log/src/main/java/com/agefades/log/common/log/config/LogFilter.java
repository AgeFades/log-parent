package com.agefades.log.common.log.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.agefades.log.common.core.constants.CommonConstant;
import com.agefades.log.common.core.util.GrayContextUtil;
import com.agefades.log.common.core.util.LogUtil;
import com.agefades.log.common.core.util.dto.SysUserDTO;
import com.agefades.log.common.core.util.UserInfoContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 日志TraceId、用户信息、灰度标记...等信息透传 过滤器
 *
 * @author DuChao
 * @date 2020/9/10 3:13 下午
 */
@Slf4j
@Order(1)
@Configuration
public class LogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头取traceId，取到值即为其他服务调用，属于traceId的传递
        String traceId = request.getHeader(CommonConstant.TRACE_ID);
        // setTraceId() 方法中，如果参数为空，则会自己生成一个uuid当做traceId，即第一个进入的服务自行生成traceId
        LogUtil.setTraceId(traceId);

        // 灰度标记
        String gray = request.getHeader(CommonConstant.GRAY);
        GrayContextUtil.setGrayTag(Convert.toBool(gray));

        // 处理网关或其他服务传递的用户信息
        String userInfoStr = request.getHeader(CommonConstant.HEADER_SYS_USER);
        if (StrUtil.isNotBlank(userInfoStr)) {
            String decode = URLDecoder.decode(userInfoStr, StandardCharsets.UTF_8);
            // 放入业务系统上下文,方便业务使用
            SysUserDTO sysUserDTO = JSONUtil.toBean(decode, SysUserDTO.class);
            UserInfoContextUtil.setSysUserDTO(sysUserDTO);

            // 用户id放入日志线程上下文
            LogUtil.setUserId(sysUserDTO.getId());
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            LogUtil.clearAll();
            UserInfoContextUtil.reset();
            GrayContextUtil.reset();
        }
    }

}
