package com.zhengcheng.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    @JSONField(serialize = false)
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private boolean deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
