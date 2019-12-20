package com.zhengcheng.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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

    @JSONField(serialize = false)
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private boolean deleted;
}
