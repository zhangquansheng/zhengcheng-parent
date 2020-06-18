package com.zhengcheng.mybatis.model;

import com.zhengcheng.mybatis.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 逻辑删除
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/20 23:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeletedEntity extends BaseEntity {

    /**
     * 逻辑删除,1 表示删除， 0 表示未删除
     */
    @TableField("is_deleted")
    private boolean deleted;
}
