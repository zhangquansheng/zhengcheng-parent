package com.zhengcheng.common.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 安全用户
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.authserver.serce
 * @Description :
 * @date :    2019/3/26 1:19
 */
@Data
public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = 4872628781561412463L;

    private Long userId;

    private String mobile;

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

