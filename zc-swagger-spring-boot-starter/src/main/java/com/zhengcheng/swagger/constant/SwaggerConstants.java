package com.zhengcheng.swagger.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Swagger 常量
 *
 * @author :    zhangquansheng
 * @date :    2020/6/6 9:38
 */
public class SwaggerConstants {

    public static final List<String> PATTERNS = Arrays.asList(
            "/swagger-ui.html", "/swagger-resources",
            "/swagger-resources/**", "/webjars/**", "/v2/api-docs",
            "/configuration/ui", "/configuration/security");

}
