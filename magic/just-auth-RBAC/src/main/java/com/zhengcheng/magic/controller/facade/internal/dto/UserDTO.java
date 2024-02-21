package com.zhengcheng.magic.controller.facade.internal.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户(User)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -87464380427076695L;

    @ApiModelProperty("用户号")
    private String userNo;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("最后登录时间")
    private String lastLogin;
    @ApiModelProperty("角色列表")
    private List<String> roleCodes;
    @ApiModelProperty("权限列表")
    private List<String> authorityCodes;
}