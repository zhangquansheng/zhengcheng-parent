package com.zhengcheng.ums.dto.command;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RoleAuthorityCommand
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleAuthorityCommand implements Serializable {
    private static final long serialVersionUID = 808453051183645421L;

    @ApiModelProperty("角色ID")
    private Long              roleId;

    @ApiModelProperty("权限ID列表")
    private String            authorityIds;

}
