package com.zhengcheng.ums.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限表(Authority)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityDTO implements Serializable {
    private static final long serialVersionUID = -71655364525774779L;

    @ApiModelProperty("ID")
    private Long              id;
    @ApiModelProperty("名称")
    private String            name;
    @ApiModelProperty("编码")
    private String            code;
    @ApiModelProperty("前端路由")
    private String            route;
    @ApiModelProperty("图标")
    private String            icon;
    @ApiModelProperty("父ID")
    private Long              pid;
    @ApiModelProperty("层级(最多三级1,2,3)")
    private Integer           level;
    @ApiModelProperty("后端接口URL")
    private String            url;
    @ApiModelProperty("备注")
    private String            remark;
    @ApiModelProperty("类型，0-菜单权限，1-按钮权限")
    private Integer           type;
    @ApiModelProperty("排序")
    private Integer           sort;
    @ApiModelProperty("是否启用")
    private boolean           enable;
    @ApiModelProperty("是否禁用，对应 enable 字段，dtree使用")
    private boolean           disabled;
    @ApiModelProperty("是否选中，0-否，1-是")
    private String            checkArr;
}
