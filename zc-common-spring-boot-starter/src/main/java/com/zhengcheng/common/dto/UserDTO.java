package com.zhengcheng.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用户(User)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -87464380427076695L;

    /**
     * ID
     */
    private Long id;
    /**
     * 用户号
     */
    private String userNo;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 是否启用
     */
    private boolean enable;
    /**
     * 最后登录时间 yyyy-MM-dd HH:mm:ss
     */
    private String lastLogin;
    /**
     * 角色列表
     */
    private List<String> roleCodes;
    /**
     * 权限列表
     */
    private List<String> authorityCodes;
}
