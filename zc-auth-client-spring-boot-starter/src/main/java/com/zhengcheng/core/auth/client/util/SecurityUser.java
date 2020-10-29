package com.zhengcheng.core.auth.client.util;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 安全登录用户
 *
 * @author :    quansheng.zhang
 * @date :    2019/10/8 11:30
 */
@Data
public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = -8677914563822205507L;
    private Long userId;
    private String mobile;
    private String name;
    private String nickname;
    private Collection<GrantedAuthority> authorities;
    private String password;
    private String username;
    private Boolean isEnabled;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
