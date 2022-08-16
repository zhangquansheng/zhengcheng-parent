package com.zhengcheng.cache.idempotent.expression;

import com.zhengcheng.cache.idempotent.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * KeyResolver
 *
 * @author quansheng.zhang
 * @since 2022/8/16 15:52
 */
public interface KeyResolver {

    String resolver(Idempotent idempotent, JoinPoint point);

}
