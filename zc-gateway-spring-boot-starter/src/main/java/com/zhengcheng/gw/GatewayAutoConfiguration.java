package com.zhengcheng.gw;

import com.zhengcheng.gw.swagger.config.SwaggerResourceConfig;
import com.zhengcheng.gw.swagger.controller.SwaggerHandler;
import com.zhengcheng.gw.swagger.filter.SwaggerHeaderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring Cloud Gateway 自动配置
 *
 * @author quansheng1.zhang
 * @since 2022/05/28 13:01
 */
@Import({SwaggerResourceConfig.class, SwaggerHandler.class, SwaggerHeaderFilter.class})
@Configuration
@RequiredArgsConstructor
public class GatewayAutoConfiguration {

}
