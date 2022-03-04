package com.agefades.log.system.common.req;

import com.agefades.log.common.datasource.base.PageReq;
import com.agefades.log.system.common.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统用户分页条件查询Vo")
public class SysUserQueryReq extends PageReq<SysUser> {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("状态: 0停用 1启用")
    private Integer isEnable;

}
