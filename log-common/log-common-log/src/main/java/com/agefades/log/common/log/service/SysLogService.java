package com.agefades.log.common.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.agefades.log.common.log.entity.SysLog;
import com.agefades.log.common.log.req.SysLogQueryReq;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

public interface SysLogService extends IService<SysLog> {

    /**
     * 切面存储操作日志
     * @param point : 切面point
     * @param e : 异常
     * @param l : 耗时时间
     * @param methodType : 请求类型
     * @param ip : ip
     * @param username : 当前登录用户名
     */
    @Async
    void saveLog(ProceedingJoinPoint point, Throwable e, long l, String methodType, String ip, String username);

    /**
     * 多条件分页查询
     */
    Page<SysLog> conditionPage(SysLogQueryReq req);
}
