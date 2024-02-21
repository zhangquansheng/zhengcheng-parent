package com.zhengcheng.magic.controller.command;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * RoleAuthorityCommand
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:57:03
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleAuthorityCommand extends UserCommand {
    private static final long serialVersionUID = 400471724728629609L;

    @ApiModelProperty("角色ID（role表ID）")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @ApiModelProperty("权限ID（authority表ID）列表")
    @NotEmpty(message = "权限ID列表不能为空")
    private List<Long> authorityIds;

}
