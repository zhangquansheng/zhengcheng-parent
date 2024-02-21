package com.zhengcheng.ums.dto.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 发送手机验证码
 *
 * @author quansheng1.zhang
 * @since 2022/9/22 11:53
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendSmsCommand implements Serializable {

    @ApiModelProperty("手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

}
