package com.zhengcheng.ums.dto;

import com.zhengcheng.common.dto.BaseDTO;

import lombok.*;

/**
 * UserLoginLogDTO
 *
 * @author quansheng1.zhang
 * @since 2022/5/1 21:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginLogDTO extends BaseDTO {

    private static final long serialVersionUID = -6794033241754576836L;

    /**
     * 用户名
     */
    private String            username;
    /**
     * 姓名
     */
    private String            name;
    /**
     * 用户ID(user表ID)
     */
    private Long              userId;
    /**
     * 操作类型，0-系统登录，1-系统登出
     */
    private Integer           type;
    /**
     * 操作类型描述
     */
    private String            typeDesc;
    /**
     * 服务器地址
     */
    private String            serverIp;
    /**
     * 登录结果，0-成功，1-失败
     */
    private Integer           result;
    /**
     * 登录结果描述
     */
    private String            resultDesc;
    /**
     * 登录IP地址
     */
    private String            loginIp;
    /**
     * 操作内容
     */
    private String            content;
}
