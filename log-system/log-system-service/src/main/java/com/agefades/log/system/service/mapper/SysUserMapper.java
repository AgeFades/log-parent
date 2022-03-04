package com.agefades.log.system.service.mapper;

import com.agefades.log.system.common.dto.ClientMenuDTO;
import com.agefades.log.system.common.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<ClientMenuDTO> getPermission(@Param("userId") String userId);

}
