package com.zhengcheng.ums.dto.command;

import com.zhengcheng.common.utils.PageCommand;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private String typeCode;
}
