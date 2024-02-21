package com.zhengcheng.magic.controller.facade.internal.dto;

import lombok.*;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 角色权限表(RoleAuthority)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:57:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleAuthorityDTO implements Serializable {
    private static final long serialVersionUID = 601130778864953952L;
    @ApiModelProperty("角色ID（role表ID）")
    private Long roleId;
    @ApiModelProperty("权限ID（authority表ID）")
    private Long authorityId;

}
