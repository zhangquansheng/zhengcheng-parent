package com.zhengcheng.core.apollo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * ApolloRefreshConfiguration
 *
 * @author quansheng1.zhang
 * @since 2020/11/5 19:28
 */
@Slf4j
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@AutoConfigureOrder
@ConditionalOnProperty(value = "zc.apollo.refresh.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(RefreshScope.class)
public class SpringBootApolloRefreshConfiguration implements ApplicationContextAware {

    ApplicationContext applicationContext;

    private final RefreshScope refreshScope;

    public SpringBootApolloRefreshConfiguration(final RefreshScope refreshScope) {
        this.refreshScope = refreshScope;

        log.info("------ SpringBoot Apollo 自动刷新配置 -----------------------------");
        log.info("------ 默认配置 zc.apollo.refresh.enabled = true --------");
        log.info("----------------------------------------------------------------------------------------------------------------------");
    }

    @Value("#{'${zc.apollo.refresh.name:}'.split(',')}")
    private List<String> names;

    /**
     * 这里指定Apollo的namespace，非常重要，如果不指定，默认只使用application
     *
     * @param changeEvent ConfigChangeEvent
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        for (String changedKey : changeEvent.changedKeys()) {
            log.info("apollo changed namespace:{} Key:{} value:{}", changeEvent.getNamespace(), changedKey, changeEvent.getChange(changedKey));
        }

        refreshProperties(changeEvent);
    }

    public void refreshProperties(ConfigChangeEvent changeEvent) {
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));

        //使用 refreshScope.refreshAll(); 的风险很大，我们已经在生产上遇到过很多次事务相关的空值报错。
        if (CollectionUtil.isNotEmpty(names)) {
            names.forEach(name -> {
                if (StrUtil.isNotBlank(name)) {
                    refreshScope.refresh(name);
                    log.info("refresh bean {} success.", name);
                }
            });
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

}
