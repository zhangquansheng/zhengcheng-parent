package com.zhengcheng.auth.client;

import com.google.common.collect.Lists;
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
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    private List<String> DEFAULT_PERMIT_ALL = Lists.newArrayList(
            "/favicon.ico",
            "/oauth/**",
            "/webjars/**",
            "/resources/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v2/api-docs");

    @Autowired
    private SecurityOauth2Properties securityOauth2Properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        Assert.notNull(securityOauth2Properties.getResourceId(), "resourceId is required");
        List<String> permitAll = securityOauth2Properties.getPermitAll();
        permitAll.addAll(DEFAULT_PERMIT_ALL);
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(permitAll.toArray(new String[permitAll.size()])).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    private CustomOauth2AccessDeniedHandler customoAuth2AccessDeniedHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(securityOauth2Properties.getResourceId())
                .stateless(true)
                .authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(customoAuth2AccessDeniedHandler);
    }
}
