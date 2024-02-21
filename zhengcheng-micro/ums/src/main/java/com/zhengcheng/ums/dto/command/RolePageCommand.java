package com.zhengcheng.ums.dto.command;

import com.zhengcheng.common.web.PageCommand;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * RolePageCommand
 *
 * @author quansheng1.zhang
 * @since 2022/5/16 22:26
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePageCommand extends PageCommand {
    private static final long serialVersionUID = -8093413668021819241L;

    @ApiModelProperty("角色名称")
    private String            name;
}
