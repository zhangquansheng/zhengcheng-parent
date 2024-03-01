package com.zhengcheng.satoken.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo implements Serializable {
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
     * 客户端IP
     */
    private String clientIp;
}
