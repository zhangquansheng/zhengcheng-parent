package com.zhengcheng.common.security;

import com.zhengcheng.common.dto.CurrentUserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

/**
 * 安全工具
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/26 22:11
 */
public class SecurityUtil {

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static CurrentUserDto getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CurrentUserDto currentUserDto = new CurrentUserDto();
        if (principal instanceof SecurityUser) {
            SecurityUser securityUser = ((SecurityUser) principal);
            currentUserDto.setId(securityUser.getUserId());
            currentUserDto.setUsername(securityUser.getUsername());
            currentUserDto.setNickname(securityUser.getNickname());
            currentUserDto.setMobile(securityUser.getMobile());
            return currentUserDto;
        }
        return null;
    }

    /**
     * 获取当前登录的用户名
     *
     * @return
     */
    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        if (principal instanceof Principal) {
            return ((Principal) principal).getName();
        }
        return String.valueOf(principal);
    }
}
