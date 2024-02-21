package com.zhengcheng.ums.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 地区表
 *
 * @author quansheng1.zhang
 * @since 2022/5/10 10:54
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("area")
public class Area extends BaseEntity<Area> {

    private static final long serialVersionUID = 370782961893415177L;
    /**
     * 父id
     */
    private Long              pid;
    /**
     * 简称
     */
    private String            shortname;
    /**
     * 名称
     */
    private String            name;
    /**
     * 全称
     */
    private String            mergename;
    /**
     * 层级 0 1 2 省市区县
     */
    private Integer           level;
    /**
     * 拼音
     */
    private String            pinyin;
    /**
     * 长途区号
     */
    private String            code;
    /**
     * 邮编
     */
    private String            zip;
    /**
     * 首字母
     */
    private String            first;
    /**
     * 经度
     */
    private String            lng;
    /**
     * 纬度
     */
    private String            lat;
}
