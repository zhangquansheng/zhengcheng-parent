package com.zhengcheng.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The class Base entity.
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity<T extends Model<?>> extends Model<T> {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
