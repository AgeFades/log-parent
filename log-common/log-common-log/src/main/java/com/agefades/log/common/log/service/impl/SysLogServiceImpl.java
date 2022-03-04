package com.agefades.log.common.log.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.agefades.log.common.core.enums.BoolEnum;
import com.agefades.log.common.core.util.AspectUtil;
import com.agefades.log.common.core.util.LogUtil;
import com.agefades.log.common.log.entity.SysLog;
import com.agefades.log.common.log.mapper.SysLogMapper;
import com.agefades.log.common.log.req.SysLogQueryReq;
import com.agefades.log.common.log.service.SysLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public void saveLog(ProceedingJoinPoint point, Throwable e, long l, String methodType, String ip, String username) {
        // 是否异常
        boolean isError = e != null;

        // 获取日志描述
        MethodSignature signature = (MethodSignature) point.getSignature();
        String logDesc = signature.getMethod().getAnnotation(com.agefades.log.common.log.annotation.SysLog.class).desc();

        // 构建操作日志对象
        SysLog sysLog = SysLog.builder()
                .resultType(isError ? BoolEnum.N.getEnumStr() : BoolEnum.Y.getEnumStr())
                .errorMsg(isError ? StrUtil.sub(e.getMessage(), 0, 250) : null)
                .ip(ip)
                .username(username)
                .methodType(methodType)
                .elapsedTime(l + "毫秒")
                .className(StrUtil.subAfter(point.getSignature().getDeclaringTypeName(), '.', true))
                .methodName(point.getSignature().getName())
                .logDesc(logDesc)
                .params(AspectUtil.getParams(point))
                .traceId(LogUtil.getTraceId())
                .build();

        log.info("操作日志: {}", JSONUtil.toJsonStr(sysLog));
        save(sysLog);
    }

    @Override
    public Page<SysLog> conditionPage(SysLogQueryReq req) {
        return lambdaQuery()
                .like(StrUtil.isNotBlank(req.getUsername()), SysLog::getUsername, req.getUsername())
                .like(StrUtil.isNotBlank(req.getLogDesc()), SysLog::getLogDesc, req.getLogDesc())
                .eq(StrUtil.isNotBlank(req.getResultType()), SysLog::getResultType, req.getResultType())
                .eq(StrUtil.isNotBlank(req.getTraceId()), SysLog::getTraceId, req.getTraceId())
                .ge(ObjectUtil.isNotNull(req.getStartTime()), SysLog::getCreateTime, req.getStartTime())
                .le(ObjectUtil.isNotNull(req.getEndTime()), SysLog::getCreateTime, req.getEndTime())
                .page(req.page());
    }

}
