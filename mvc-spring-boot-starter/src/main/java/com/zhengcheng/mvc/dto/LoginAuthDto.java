/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：LoginAuthDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.zhengcheng.mvc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录人信息
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.dto
 * @Description :
 * @date :    2019/3/21 18:08
 */
@Data
@ApiModel(value = "登录人信息")
public class LoginAuthDto implements Serializable {

    private static final long serialVersionUID = -1137852221455042256L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
