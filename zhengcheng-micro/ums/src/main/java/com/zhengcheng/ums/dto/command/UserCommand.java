package com.zhengcheng.ums.dto.command;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.zhengcheng.common.validation.annotation.Update;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户(User)数据查询对象
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:50
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCommand implements Serializable {
    private static final long  serialVersionUID = -58148876498682370L;

    /**
     * 用户来源于 admin 后台
     */
    public static final String SOURCE_ADMIN     = "admin";

    @ApiModelProperty("ID")
    @NotNull(message = "ID不能为空", groups = { Update.class })
    private Long               id;
    @ApiModelProperty("用户名")
    private String             username;
    @ApiModelProperty("邮箱")
    private String             email;
    @ApiModelProperty("手机号")
    private String             mobile;
    @ApiModelProperty("姓名")
    private String             name;
    @ApiModelProperty("密码")
    private String             password;
    @ApiModelProperty("头像")
    private String             avatar;
    @ApiModelProperty("是否启用")
    private Boolean            enable;
    @ApiModelProperty("来源：admin-管理后台,用户注册-userReg")
    private String             source;
    @ApiModelProperty("角色ID列表")
    private List<Long>         roleIds;
}
