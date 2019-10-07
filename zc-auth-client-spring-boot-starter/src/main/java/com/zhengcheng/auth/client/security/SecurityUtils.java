package com.zhengcheng.auth.client.security;

import com.zhengcheng.common.enumeration.CodeEnum;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.model.CurrentUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/26 22:11
 */
@Slf4j
public class SecurityUtils {

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
            currentUserDto.setUserId(securityUser.getUserId());
            currentUserDto.setAdminId(securityUser.getAdminId());
            currentUserDto.setSingleId(securityUser.getSingleId());
            currentUserDto.setNickname(securityUser.getNickname());
            currentUserDto.setName(securityUser.getName());
            currentUserDto.setOpenId(securityUser.getOpenId());
            return currentUserDto;
        }
        throw new BizException(CodeEnum.UNAUTHORIZED.getCode(), CodeEnum.UNAUTHORIZED.getMessage());
    }

}