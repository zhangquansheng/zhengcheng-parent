package com.zhengcheng.ums.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 数据字典
 *
 * @author quansheng1.zhang
 * @since 2022/4/28 20:22
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("dict_data")
public class DictData extends BaseEntity<DictData> {
    private static final long serialVersionUID = 6586961943336528330L;
    /**
     * 姓名
     */
    private String            name;
    /**
     * 值
     */
    private String            value;
    /**
     * 字典类型编码
     */
    private String            typeCode;
    /**
     * 是否默认,1-默认，0-非默认
     */
    private Integer           isDefault;
    /**
     * 是否启用
     */
    @TableField(value = "is_enable")
    private Boolean           enable;
    /**
     * 备注
     */
    private String            remark;
    /**
     * 参数
     */
    private String            params;
}
