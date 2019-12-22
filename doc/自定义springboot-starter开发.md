### SpringBoot提供的条件化注解：

条件化注解 |	配置生效条件
---|---
@ConditionalOnBean |	配置了某个特定Bean
@ConditionalOnMissingBean |	没有配置特定的Bean
@ConditionalOnClass |	Classpath里有指定的类
@ConditionalOnMissingClass |	Classpath里缺少指定的类
@ConditionalOnExpression |	给定的SpEL表达式计算结果为true
@ConditionalOnJava |	Java的版本匹配特定值或者一个范围值
@ConditionalOnJndi |	参数中给定的JNDI位置必须存在一个，如果没有参数，则需要JNDI InitialContext
@ConditionalOnProperty |	指定的配置属性要有一个明确的值
@ConditionalOnResource |	Classpath里有指定的资源
@ConditionalOnWebApplication |	这是一个Web应用程序
@ConditionalOnNotWebApplication |	这不是一个Web应用程序