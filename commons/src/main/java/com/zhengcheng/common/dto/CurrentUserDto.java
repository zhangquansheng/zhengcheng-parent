package com.zhengcheng.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 当前登录用户
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.dto
 * @Description :
 * @date :    2019/3/26 1:48
 */
@Data
public class CurrentUserDto implements Serializable {
    private static final long serialVersionUID = -4968088026487026460L;
    private Long id;
    private String username;
    private String mobile;
    private String nickname;
}
