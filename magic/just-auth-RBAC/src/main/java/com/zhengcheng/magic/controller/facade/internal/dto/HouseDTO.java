package com.zhengcheng.magic.controller.facade.internal.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小天才基础表-房(House)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-05-13 11:41:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseDTO implements Serializable {
    private static final long serialVersionUID = -15935735925513620L;

    @ApiModelProperty("楼盘ID")
    private Integer lpId;
    @ApiModelProperty("备案号")
    private String recordNum;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("楼号")
    private String buildingNum;
    @ApiModelProperty("建筑面积")
    private String builtUpArea;
    @ApiModelProperty("套数")
    private Integer roomNum;
    @ApiModelProperty("均价")
    private String averagePrice;
    @ApiModelProperty("坐落位置")
    private String lpLocation;
    @ApiModelProperty("所在区域")
    private String lpArea;
    @ApiModelProperty("物业类型")
    private String wyType;
    @ApiModelProperty("楼盘物业")
    private String wyEnterprise;
    @ApiModelProperty("周边配套")
    private String rim;
    @ApiModelProperty("交通状况")
    private String traffic;
}