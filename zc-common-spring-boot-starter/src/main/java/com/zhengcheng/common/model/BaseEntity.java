package com.zhengcheng.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * The class Base entity.
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Data
public class BaseEntity<T extends Model<?>> extends Model<T> {
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Boolean deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
