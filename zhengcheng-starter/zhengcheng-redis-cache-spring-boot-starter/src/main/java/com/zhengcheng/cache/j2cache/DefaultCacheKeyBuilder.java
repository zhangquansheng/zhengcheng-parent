package com.zhengcheng.cache.j2cache;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * 默认的redis缓存key生成器
 *
 * @author quansheng1.zhang
 * @since 2023/11/1 14:46
 */
public class DefaultCacheKeyBuilder {

    private static final DefaultParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    private final BeanResolver beanResolver;

    public DefaultCacheKeyBuilder(BeanFactory beanFactory) {
        this.beanResolver = new BeanFactoryResolver(beanFactory);
    }

    /**
     * 支持 SPEL 表达式构建key
     *
     * @param method         方法
     * @param args           入参值
     * @param definitionKeys 定义的key值
     * @return 返回解析后的key
     */
    public String buildKey(Method method, Object[] args, String[] definitionKeys) {
        if (definitionKeys.length > 1 || !"".equals(definitionKeys[0])) {
            // 使用Spring的DefaultParameterNameDiscoverer获取方法形参名数组
            String[] paramNames = NAME_DISCOVERER.getParameterNames(method);
            if (Objects.isNull(paramNames)) {
                return "";
            }

            // Spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < args.length; i++) {
                String paramName = paramNames[i];
                if (Objects.nonNull(paramName)) {
                    context.setVariable(paramName, args[i]);
                }
            }

            List<String> definitionKeyList = new ArrayList<>(definitionKeys.length);
            for (String definitionKey : definitionKeys) {
                if (definitionKey != null && !definitionKey.isEmpty()) {
                    // 解析过后的Spring表达式对象
                    String key = PARSER.parseExpression(definitionKey).getValue(context, String.class);
                    if (!CharSequenceUtil.isNullOrUndefined(key) && CharSequenceUtil.isNotBlank(key)) {
                        definitionKeyList.add(key);
                    }
                }
            }
            return StringUtils.collectionToDelimitedString(definitionKeyList, ".", "", "");
        }
        return "";
    }

}
