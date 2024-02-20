package com.zhengcheng.data.elasticsearch.annotations;

import com.zhengcheng.data.elasticsearch.ElasticsearchAutoConfiguration;
import com.zhengcheng.data.elasticsearch.registrar.DocumentScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Use this annotation to register EnableElasticsearchPlus property sources when using Java Config.
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 19:21
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ElasticsearchAutoConfiguration.class, DocumentScannerRegistrar.class})
public @interface EnableElasticsearchPlus {

    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation declarations e.g.:
     * {@code @EnableElasticsearchPlus("org.my.pkg")} instead of
     * {@code @EnableElasticsearchPlus(basePackages = "org.my.pkg"})}.
     *
     * @return base package names
     */
    String[] value() default {};

    /**
     * Base packages to scan for EnableElasticsearchPlus interfaces. Note that only interfaces with at least one method
     * will be registered; concrete classes will be ignored.
     *
     * @return base package names for scanning mapper interface
     */
    String[] basePackages() default {};
}
