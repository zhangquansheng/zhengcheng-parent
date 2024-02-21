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
public class DictDataDTO implements Serializable {
    private static final long serialVersionUID = 1007458983411248729L;

    @ApiModelProperty("ID")
    private Long              id;
    @ApiModelProperty("名称")
    private String            name;
    @ApiModelProperty("值")
    private String            value;
    @ApiModelProperty("字典类型编码")
    private String            typeCode;
    @ApiModelProperty("是否默认,1-默认，0-非默认")
    private Integer           isDefault;
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
