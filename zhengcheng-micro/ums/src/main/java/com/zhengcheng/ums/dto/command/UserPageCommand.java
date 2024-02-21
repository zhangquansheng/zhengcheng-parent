package com.zhengcheng.ums.dto.command;


import com.zhengcheng.common.utils.PageCommand;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * UserPageCommand
 *
 * @author quansheng1.zhang
 * @since 2022/4/28 17:35
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPageCommand extends PageCommand {

    private static final long serialVersionUID = 776269931828505584L;
    @ApiModelProperty("用户名")
    private String            username;
    @ApiModelProperty("姓名")
    private String            name;
    @ApiModelProperty("手机号")
    private String            mobile;
}
