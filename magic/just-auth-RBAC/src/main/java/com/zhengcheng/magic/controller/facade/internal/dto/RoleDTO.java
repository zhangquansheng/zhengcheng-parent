package com.zhengcheng.magic.controller.facade.internal.dto;

import com.zhengcheng.common.domain.BaseDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 角色表(Role)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO extends BaseDTO {
    private static final long serialVersionUID = 949066785534806845L;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("角色编码")
    private String code;
    @ApiModelProperty("是否内置")
    private Integer isSystem;
    @ApiModelProperty("描述")
    private String description;

}
