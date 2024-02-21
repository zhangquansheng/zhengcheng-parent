package com.zhengcheng.ums.dto.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * UserLoginCommand
 *
 * @author quansheng1.zhang
 * @since 2022/7/1 22:02
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginCommand implements Serializable {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty("验证码的KEY")
    @NotBlank(message = "验证码的KEY不能为空")
    private String uuid;
    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
