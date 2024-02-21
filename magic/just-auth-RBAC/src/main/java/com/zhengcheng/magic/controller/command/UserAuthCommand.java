package com.zhengcheng.magic.controller.command;

import com.zhengcheng.common.validation.annotation.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * 用户授权表(UserAuth)数据查询对象
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 17:28:11
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAuthCommand extends UserCommand {
    private static final long serialVersionUID = 608391577316415445L;

    @ApiModelProperty("ID，更新时候必填")
    @NotNull(message = "ID不能为空", groups = {Update.class})
    private Long id;

    @ApiModelProperty("用户ID(user表ID)")
    private Long userId;

    @ApiModelProperty("社会化用户ID(social_user表ID)")
    private Long socialUserId;

}
