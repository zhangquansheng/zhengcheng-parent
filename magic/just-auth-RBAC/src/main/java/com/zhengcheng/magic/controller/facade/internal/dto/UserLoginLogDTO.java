package com.zhengcheng.magic.controller.facade.internal.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录日志表(UserLoginLog)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 19:49:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginLogDTO implements Serializable {
    private static final long serialVersionUID = 519532655171461089L;

    @ApiModelProperty("用户ID(user表ID)")
    private Long userId;
    @ApiModelProperty("操作类型，0-系统登录，1-系统登出")
    private Integer type;
    @ApiModelProperty("服务器地址")
    private String serverIp;
    @ApiModelProperty("登录结果，0-成功，1-失败")
    private Integer result;
    @ApiModelProperty("登录IP地址")
    private String loginIp;
    @ApiModelProperty("操作内容")
    private String content;
    @ApiModelProperty("登录时间")
    private String loginTime;
}