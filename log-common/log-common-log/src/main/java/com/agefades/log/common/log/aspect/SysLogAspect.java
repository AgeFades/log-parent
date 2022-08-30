package com.agefades.log.common.log.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.agefades.log.common.core.util.IpUtil;
import com.agefades.log.common.log.service.SysLogService;
import com.agefades.log.common.core.util.UserInfoContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 系统日志注解处理切面
 *
 * @author DuChao
 * @date 2020/7/24 10:23 上午
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    SysLogService sysLogService;

    private final ThreadLocal<TimeInterval> timer = new ThreadLocal<>();

    /**
     * 切点 SysLog 注解
     */
    @Pointcut("@annotation(com.agefades.log.common.log.annotation.SysLog)")
    public void sysLog() {
    }

    /**
     * 环绕通知
     */
    @Around("sysLog()")
    public Object sysLogAround(ProceedingJoinPoint point) throws Throwable {
        // 调用目标方法，方法计时，单位毫秒
        timer.set(DateUtil.timer());
        Object result = point.proceed();
        saveLog(point, null);
        return result;
    }

    @AfterThrowing(pointcut = "sysLog()", throwing = "e")
    public void sysLogThrowing(JoinPoint joinPoint, Throwable e) {
        saveLog((ProceedingJoinPoint) joinPoint, e);
    }

    private void saveLog(ProceedingJoinPoint point, Throwable e) {
        sysLogService.saveLog(point, e, timer.get().interval(), getReqMethodType(), IpUtil.getIpAddr(), UserInfoContextUtil.getSysUserName());
        timer.remove();
    }

    /**
     * 获取请求类型
     */
    private String getReqMethodType() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest().getMethod() : null;
    }

}
