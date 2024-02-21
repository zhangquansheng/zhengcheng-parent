package com.zhengcheng.magic.controller.command;

import com.zhengcheng.common.validation.annotation.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 权限表(Authority)数据查询对象
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorityCommand extends UserCommand {
    private static final long serialVersionUID = 808453051183645421L;

    @ApiModelProperty("ID，更新时候必填")
    @NotNull(message = "ID不能为空", groups = {Update.class})
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("前端路由")
    private String route;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("父ID")
    private Long pid;

    @ApiModelProperty("后端接口URL")
    private String url;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("类型，0-菜单权限，1-按钮权限")
    private Integer type;

    @ApiModelProperty("排序")
    private Integer sort;

}
