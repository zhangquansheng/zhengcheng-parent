package com.zhengcheng.magic.controller.facade.internal.dto;

import lombok.*;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 用户授权表(UserAuth)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 17:28:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthDTO implements Serializable {
    private static final long serialVersionUID = 809348928343168776L;
    @ApiModelProperty("用户ID(user表ID)")
    private Long userId;
    @ApiModelProperty("社会化用户ID(social_user表ID)")
    private Long socialUserId;

}
