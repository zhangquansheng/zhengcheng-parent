package com.zhengcheng.auth.client;

import com.zhengcheng.auth.client.handler.AuthExceptionEntryPoint;
import com.zhengcheng.auth.client.handler.CustomOauth2AccessDeniedHandler;
import com.zhengcheng.auth.client.properties.SecurityOauth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源服务自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/24 14:12
 */
@Import({CustomOauth2AccessDeniedHandler.class})
@EnableConfigurationProperties({SecurityOauth2Properties.class})
@Configuration
@EnableResourceServer
@EnableOAuth2Client
public class ResourceServerAutoConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] permitAllUrl = new String[]{"/favicon.ico",
                "/oauth/**",
                "/webjars/**",
                "/resources/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs"};
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(permitAllUrl).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

//    @Value("${security.oauth2.resource.id}")
//    private String resourceId;

    @Autowired
    private CustomOauth2AccessDeniedHandler customoAuth2AccessDeniedHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("user")
                .stateless(true)
                .authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(customoAuth2AccessDeniedHandler);
    }
}
