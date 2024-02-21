package com.zhengcheng.magic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.magic.domain.enums.AuthorityTypeEnum;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 权限表(Authority)实体类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:57
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("authority")
public class Authority extends BaseEntity<Authority> {
    private static final long serialVersionUID = -34315871269614900L;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 前端路由
     */
    private String route;
    /**
     * 图标
     */
    private String icon;
    /**
     * 父ID
     */
    private Long pid;
    /**
     * 数路径
     */
    private String treePath;
    /**
     * 层级(最多三级1,2,3)
     */
    private Integer level;
    /**
     * 后端接口URL
     */
    private String url;
    /**
     * 备注
     */
    private String remark;
    /**
     * 类型，0-菜单权限，1-按钮权限
     */
    private AuthorityTypeEnum type;
    /**
     * 排序
     */
    private Integer sort;

}
