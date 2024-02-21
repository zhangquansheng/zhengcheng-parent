package com.zhengcheng.ums.dto.command;

import javax.validation.constraints.NotNull;

import com.zhengcheng.common.web.PageCommand;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * DictDataPageCommand
 *
 * @author quansheng1.zhang
 * @since 2022/4/28 17:35
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DictDataPageCommand extends PageCommand {

    private static final long serialVersionUID = -876860583610487247L;
    @ApiModelProperty("字典类型编码")
    @NotNull(message = "字典类型编码不能为空")
    private String            typeCode;
}
