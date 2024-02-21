package com.zhengcheng.ums.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DictTypeDTO
 *
 * @author quansheng1.zhang
 * @since 2022/4/28 20:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DictTypeDTO implements Serializable {
    private static final long serialVersionUID = 1007458983411248729L;

    @ApiModelProperty("ID")
    private Long              id;
    @ApiModelProperty("名称")
    private String            name;
    @ApiModelProperty("编码")
    private String            code;
    @ApiModelProperty("描述")
    private String            description;
    @ApiModelProperty("是否启用")
    private Boolean           enable;
    @ApiModelProperty("备注")
    private String            remark;
    @ApiModelProperty("参数")
    private String            params;
    @ApiModelProperty("创建时间")
    private String            createTime;
    @ApiModelProperty("更新时间")
    private String            updateTime;
}
