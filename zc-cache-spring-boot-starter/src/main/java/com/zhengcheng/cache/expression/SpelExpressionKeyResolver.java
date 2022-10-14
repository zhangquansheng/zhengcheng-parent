package com.zhengcheng.cache.expression;

import com.google.common.base.Joiner;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * SpelExpressionKeyResolver
 *
 * @author quansheng.zhang
 * @since 2022/8/16 15:52
 */
public class SpelExpressionKeyResolver implements KeyResolver {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private static final LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    @Override
    public String resolver(String[] keys, String split, JoinPoint point) {
        Object[] arguments = point.getArgs();
        String[] params = DISCOVERER.getParameterNames(this.getMethod(point));
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (params != null && params.length > 0) {
            for (int len = 0; len < params.length; ++len) {
                context.setVariable(params[len], arguments[len]);
            }
        }

        List<String> keyResolves = new ArrayList<>();
        Expression expression;
        for (String key : keys) {
            expression = PARSER.parseExpression(key);
            keyResolves.add(expression.getValue(context, String.class));
        }
        Joiner joiner = Joiner.on(split);
        return joiner.join(keyResolves);
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException | SecurityException var5) {
                throw new RuntimeException(var5);
            }
        }

        return method;
    }

}
