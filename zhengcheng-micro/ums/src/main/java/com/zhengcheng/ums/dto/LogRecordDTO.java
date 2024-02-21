package com.zhengcheng.ums.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LogRecordDTO
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 21:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogRecordDTO implements Serializable {
    private static final long serialVersionUID = 7612262937220908365L;
    @ApiModelProperty("ID")
    private Long              id;
    @ApiModelProperty("租户")
    private String            tenant;
    @ApiModelProperty("保存的操作日志的类型，比如：订单类型、商品类型")
    private String            type;
    @ApiModelProperty("日志的子类型，比如订单的C端日志，和订单的B端日志，type都是订单类型，但是子类型不一样")
    private String            subType;
    @ApiModelProperty("日志绑定的业务标识")
    private String            bizNo;
    @ApiModelProperty("操作人")
    private String            operator;
    @ApiModelProperty("日志内容")
    private String            action;
    @ApiModelProperty("创建时间")
    private String            createTime;
    @ApiModelProperty("更新时间")
    private String            updateTime;
}
