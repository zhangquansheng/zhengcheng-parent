package com.zhengcheng.ums.dto.command;


import com.zhengcheng.common.utils.PageCommand;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * DictDataTypeCommand
 *
 * @author quansheng1.zhang
 * @since 2022/5/5 14:19
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DictTypePageCommand extends PageCommand {
    private static final long serialVersionUID = 3688076611516289020L;

    @ApiModelProperty("字典类型名称")
    private String            typeName;
}
