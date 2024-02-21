package com.zhengcheng.expression;

import org.aspectj.lang.JoinPoint;

/**
 * KeyResolver
 *
 * @author quansheng.zhang
 * @since 2022/8/16 15:52
 */
public interface KeyResolver {

    String resolver(String[] keys, String split, JoinPoint point);

}
