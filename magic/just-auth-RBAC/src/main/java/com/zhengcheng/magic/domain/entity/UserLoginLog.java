package com.zhengcheng.magic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.magic.domain.enums.LoginResultEnum;
import com.zhengcheng.magic.domain.enums.LoginTypeEnum;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 登录日志表(UserLoginLog)实体类
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 19:49:27
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_login_log")
public class UserLoginLog extends BaseEntity<UserLoginLog> {
    private static final long serialVersionUID = -95000922664567886L;
    /**
     * 用户ID(user表ID)
     */
    private Long userId;
    /**
     * 操作类型，0-系统登录，1-系统登出
     */
    private LoginTypeEnum type;
    /**
     * 服务器地址
     */
    private String serverIp;
    /**
     * 登录结果，0-成功，1-失败
     */
    private LoginResultEnum result;
    /**
     * 登录IP地址
     */
    private String loginIp;
    /**
     * 操作内容
     */
    private String content;

}