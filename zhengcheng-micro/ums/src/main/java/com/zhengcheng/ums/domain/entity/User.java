package com.zhengcheng.ums.domain.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户(User)实体类
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
public class User extends BaseEntity<User> {
    private static final long serialVersionUID = 427653819762931482L;

    /**
     * 用户名
     */
    private String            username;
    /**
     * 用户号
     */
    private String            userNo;
    /**
     * 邮箱
     */
    private String            email;
    /**
     * 手机号
     */
    private String            mobile;
    /**
     * 姓名
     */
    private String            name;
    /**
     * 密码
     */
    private String            password;
    /**
     * 头像
     */
    private String            avatar;
    /**
     * 是否启用
     */
    @TableField(value = "is_enable")
    private Boolean           enable;
    /**
     * 最后登录时间
     */
    private LocalDateTime     lastLogin;
    /**
     * 来源
     */
    private String            source;
}
