package com.zhengcheng.ums.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 操作日志表
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 19:12
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("log_record")
public class LogRecord extends BaseEntity<LogRecord> {

    private static final long serialVersionUID = 321853520437642265L;
    /**
     * 租户
     */
    private String            tenant;

    /**
     * 保存的操作日志的类型，比如：订单类型、商品类型
     */
    private String            type;
    /**
     * 日志的子类型，比如订单的C端日志，和订单的B端日志，type都是订单类型，但是子类型不一样
     */
    private String            subType;

    /**
     * 日志绑定的业务标识
     */
    private String            bizNo;
    /**
     * 操作人
     */
    private String            operator;

    /**
     * 日志内容
     */
    private String            action;
}
