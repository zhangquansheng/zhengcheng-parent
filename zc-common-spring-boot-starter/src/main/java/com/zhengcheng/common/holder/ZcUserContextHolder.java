package com.zhengcheng.common.holder;


import cn.hutool.core.util.StrUtil;
import com.zhengcheng.common.dto.UserDTO;

/**
 * 新征程框架，用户上下文 ThreadLocal
 *
 * @author :    zhngquansheng
 * @date :    2019/12/20 15:17
 */
public class ZcUserContextHolder {

    public ZcUserContextHolder() {
    }

    private static final ThreadLocal<UserDTO> userInfoLocal = new ThreadLocal<>();

    public static UserDTO getUserInfo() {
        return userInfoLocal.get();
    }

    public static void setUserInfo(UserDTO userDTO) {
        userInfoLocal.set(userDTO);
    }

    public static void removeUserInfo() {
        userInfoLocal.remove();
    }

    public static Long getUserId() {
        UserDTO userDTO = getUserInfo();
        return userDTO == null ? null : userDTO.getId();
    }

    public static String getUsername() {
        UserDTO userDTO = getUserInfo();
        return userDTO == null ? null : userDTO.getUsername();
    }

    public static String getUserNo() {
        UserDTO userDTO = getUserInfo();
        return userDTO == null ? null : userDTO.getUserNo();
    }

    public static String getOperator() {
        UserDTO userDTO = getUserInfo();
        return userDTO == null ? "" : StrUtil.format("{}({})", userDTO.getUsername(), userDTO.getUserNo());
    }

    public static String getEmail() {
        UserDTO userDTO = getUserInfo();
        return userDTO == null ? null : userDTO.getEmail();
    }

    public static String getMobile() {
        UserDTO userDTO = getUserInfo();
        return userDTO == null ? null : userDTO.getMobile();
    }
}
