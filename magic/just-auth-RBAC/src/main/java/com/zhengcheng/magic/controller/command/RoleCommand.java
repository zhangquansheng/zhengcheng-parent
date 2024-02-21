package com.zhengcheng.magic.controller.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zhengcheng.common.validation.annotation.Insert;
import com.zhengcheng.common.validation.annotation.Update;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 角色表(Role)数据查询对象
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleCommand extends UserCommand {
    private static final long serialVersionUID = 539474977453414089L;

    @ApiModelProperty("ID，更新时候必填")
    @NotNull(message = "ID不能为空", groups = {Update.class})
    private Long id;

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空", groups = {Insert.class})
    private String code;

    @ApiModelProperty("描述")
    @NotBlank(message = "描述不能为空")
    private String description;

}
