package com.zhengcheng.gw.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author quansheng1.zhang
 * @since 2022/5/28 18:20
 */
@Slf4j
@RefreshScope
@Configuration
public class SaTokenConfigure {

    @Value(value = "${sa.reactor.filter.include.path:}")
    private List<String> SA_REACTOR_FILTER_INCLUDE_PATH;
    @Value(value = "${sa.reactor.filter.exclude.path:}")
    private List<String> SA_REACTOR_FILTER_EXCLUDE_PATH;

    @Autowired
    private ObjectMapper objectMapper;

    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        List<String> excludeList = CommonConstants.CHECK_LOGIN_EXCLUDE_PATH_PATTERNS;
        excludeList.addAll(SA_REACTOR_FILTER_EXCLUDE_PATH);

        return new SaReactorFilter()
                // 拦截地址
                .setIncludeList(SA_REACTOR_FILTER_INCLUDE_PATH)
                // 开放地址
                .setExcludeList(excludeList)
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除 /user/login 用于开放登录
                    // StpUtil.checkLogin() 要求网关服务同授权登录服务共用一个redis缓存
                    SaRouter.match("/**", "/**/user/login", r -> StpUtil.checkLogin());

                })
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式 * 禁用PUT、DELETE、TRACE等方法
                            .setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,HEAD")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS).free(r -> log.info("--------OPTIONS预检请求，不做处理")).back();
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> {
                    // https://toscode.gitee.com/dromara/sa-token/issues/I46ZZF
                    SaHolder.getResponse().setHeader("Content-Type", "application/json");
                    try {
                        /**
                         * 登录相关500*  NOT_LOGIN_EXCEPTION(50008, "{0}"),
                         */
                        return objectMapper.writeValueAsString(Result.create(50008, e.getMessage()));
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }
}
