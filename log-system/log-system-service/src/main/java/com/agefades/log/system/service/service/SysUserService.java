package com.agefades.log.system.service.service;

import com.agefades.log.system.common.dto.ClientMenuDTO;
import com.agefades.log.system.common.entity.SysUser;
import com.agefades.log.system.common.req.SysUserAddReq;
import com.agefades.log.system.common.req.SysUserQueryReq;
import com.agefades.log.system.common.resp.SysUserQueryResp;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    /**
     * 分页条件查询
     */
    Page<SysUserQueryResp> conditionPage(SysUserQueryReq req);

    /**
     * 新增系统用户
     */
    void add(SysUserAddReq req);

    /**
     * 获取用户权限列表
     */
    List<ClientMenuDTO> getPermission(String userId);
}

