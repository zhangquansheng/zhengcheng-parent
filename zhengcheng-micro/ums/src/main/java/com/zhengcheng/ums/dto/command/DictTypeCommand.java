package com.zhengcheng.ums.dto.command;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.zhengcheng.common.validation.annotation.Update;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DictTypeCommand
 *
 * @author quansheng1.zhang
 * @since 2022/5/4 23:54
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DictTypeCommand implements Serializable {

    private static final long serialVersionUID = -767412246489641381L;

    @ApiModelProperty("ID，更新时候必填")
    @NotNull(message = "ID不能为空", groups = { Update.class })
    private Long              id;

    @ApiModelProperty("名称")
    private String            name;

    @ApiModelProperty("编码")
    private String            code;

    @ApiModelProperty("描述")
    private String            description;

}
