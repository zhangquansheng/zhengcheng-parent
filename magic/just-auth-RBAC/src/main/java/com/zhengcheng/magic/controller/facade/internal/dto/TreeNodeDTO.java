package com.zhengcheng.magic.controller.facade.internal.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 树节点
 *
 * @author quansheng1.zhang
 * @since 2021/8/26 11:02
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeNodeDTO implements Serializable {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("Label")
    private String label;
    @ApiModelProperty("子节点")
    private List<TreeNodeDTO> children;
}
