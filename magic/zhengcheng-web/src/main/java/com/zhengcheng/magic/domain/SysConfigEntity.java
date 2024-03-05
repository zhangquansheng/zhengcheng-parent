package com.zhengcheng.magic.domain;

//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 配置实体类
 *
 * @author zhangquansheng
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_config")
public class SysConfigEntity extends BaseEntity {

    //    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    @TableId("config_id")
    private Long configId;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

    /**
     * 所属分类的编码
     */
    private String groupCode;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
