package com.zhengcheng.core.auth.client.util;

import com.zhengcheng.core.auth.client.dto.CurrentUserDTO;
import com.zhengcheng.common.web.CodeEnum;
import com.zhengcheng.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * SecurityUtils
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/26 22:11
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录用户
     *
     * @return CurrentUserDTO
     */
    public static CurrentUserDTO getCurrentUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Object currentUserId = request.getHeader("currentUserId");
        CurrentUserDTO currentUserDto = new CurrentUserDTO();
        if (Objects.isNull(currentUserId)) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof SecurityUser) {
                SecurityUser securityUser = ((SecurityUser) principal);
                currentUserDto.setUserId(securityUser.getUserId());
                currentUserDto.setNickname(securityUser.getNickname());
                currentUserDto.setName(securityUser.getName());
                currentUserDto.setUsername(securityUser.getUsername());
                return currentUserDto;
            }
        } else {
            currentUserDto.setUserId(Long.valueOf(String.valueOf(currentUserId)));
            return currentUserDto;
        }
        throw new BizException(CodeEnum.UNAUTHORIZED.getCode(), CodeEnum.UNAUTHORIZED.getMessage());
    }

}