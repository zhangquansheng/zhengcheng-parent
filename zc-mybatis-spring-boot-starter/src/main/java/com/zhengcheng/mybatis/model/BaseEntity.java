package com.zhengcheng.mybatis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhengcheng.mybatis.annotation.TableField;
import com.zhengcheng.mybatis.annotation.TableId;
import com.zhengcheng.mybatis.enums.IdType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The class Base entity.
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:21
 */
@Data
public class BaseEntity {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "gmt_create")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "gmt_modified")
    private LocalDateTime gmtModified;
}
