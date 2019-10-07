package com.zhengcheng.auth.client.security;

import com.zhengcheng.common.model.CurrentUserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 安全用户
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/26 1:19
 */
@Data
public class SecurityUser extends CurrentUserDto implements UserDetails {

    private static final long serialVersionUID = 4872628781561412463L;

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

