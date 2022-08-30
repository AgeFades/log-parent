package com.agefades.log.system.service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.agefades.log.common.core.util.Assert;
import com.agefades.log.system.common.dto.ClientMenuDTO;
import com.agefades.log.system.common.entity.SysUser;
import com.agefades.log.system.common.req.SysUserAddReq;
import com.agefades.log.system.common.req.SysUserQueryReq;
import com.agefades.log.system.common.resp.SysUserQueryResp;
import com.agefades.log.system.service.mapper.SysUserMapper;
import com.agefades.log.system.service.service.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public Page<SysUserQueryResp> conditionPage(SysUserQueryReq req) {
        Page<SysUser> page = lambdaQuery()
                .like(StrUtil.isNotBlank(req.getPhone()), SysUser::getPhone, req.getPhone())
                .like(StrUtil.isNotBlank(req.getUsername()), SysUser::getUsername, req.getUsername())
                .eq(ObjectUtil.isNotNull(req.getIsEnable()), SysUser::getIsEnable, req.getIsEnable())
                .page(req.page());


        Page<SysUserQueryResp> result = new Page<>();
        BeanUtil.copyProperties(page, result);

        if (CollUtil.isNotEmpty(page.getRecords())) {
            result.setRecords(page.getRecords().stream()
                    .map(v -> BeanUtil.copyProperties(v, SysUserQueryResp.class))
                    .collect(Collectors.toList())
            );
        }

        return result;
    }

    @Override
    public void add(SysUserAddReq req) {
        // 1. 唯一索引校验
        Assert.isTrue(lambdaQuery().eq(SysUser::getUsername, req.getUsername()).count() == 0, "用户名重复");

        // 2. 密码加密
        req.setPassword(DigestUtil.bcrypt(req.getPassword()));

        // 3. 保存用户
        SysUser sysUser = BeanUtil.copyProperties(req, SysUser.class);
        String id = IdUtil.getSnowflake().nextIdStr();
        log.info("测试自己赋值主键: {}", id);
        sysUser.setId(id);
        save(sysUser);
    }

    @Override
    public List<ClientMenuDTO> getPermission(String userId) {
        return baseMapper.getPermission(userId);
    }

}