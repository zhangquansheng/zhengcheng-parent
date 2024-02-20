package com.zhengcheng.data.elasticsearch.registrar;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.data.elasticsearch.annotations.Document;
import com.zhengcheng.data.elasticsearch.annotations.EnableElasticsearchPlus;
import com.zhengcheng.data.elasticsearch.metadata.DocumentInfo;
import com.zhengcheng.data.elasticsearch.metadata.DocumentInfoHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义注解 @Document 扫描注册器
 *
 * @author : quansheng.zhang
 * @date : 2020/3/29 20:07
 */
@Slf4j
public class DocumentScannerRegistrar
    implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private Environment environment;

    public DocumentScannerRegistrar() {}

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 参考 org.mybatis.spring.annotation.MapperScannerRegistrar
        AnnotationAttributes annoAttrs = AnnotationAttributes
            .fromMap(importingClassMetadata.getAnnotationAttributes(EnableElasticsearchPlus.class.getName()));
        if (annoAttrs != null) {
            List<String> basePackages = new ArrayList<>();
            basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("value")).filter(StringUtils::hasText)
                .collect(Collectors.toList()));

            basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText)
                .collect(Collectors.toList()));

            if (basePackages.isEmpty()) {
                basePackages.add(getDefaultBasePackage(importingClassMetadata));
            }

            ClassPathScanningCandidateComponentProvider scanner = getScanner();
            scanner.setResourceLoader(this.resourceLoader);
            scanner.addIncludeFilter(new AnnotationTypeFilter(Document.class));

            basePackages.forEach(basePackage -> registerDocumentInfo(scanner, basePackage));
        }
    }

    private void registerDocumentInfo(ClassPathScanningCandidateComponentProvider scanner, String basePackage) {
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition)candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                String className = annotationMetadata.getClassName();
                try {
                    Class<?> beanClazz = Class.forName(className);
                    if (!beanClazz.isAnnotationPresent(Document.class)) {
                        throw new RuntimeException(StrUtil.format("{} @Document is required!", className));
                    }
                    DocumentInfo documentInfo = DocumentInfoHelper.initDocumentInfo(beanClazz);
                    log.info("Document IndexName: [{}] , IndexType: [{}].", documentInfo.getIndexName(),
                        documentInfo.getIndexType());
                } catch (ClassNotFoundException e) {
                    log.error("Could not register target class: {}", annotationMetadata.getClassName(), e);
                }
            }
        }
    }

    private static String getDefaultBasePackage(AnnotationMetadata importingClassMetadata) {
        return ClassUtils.getPackageName(importingClassMetadata.getClassName());
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (beanDefinition.getMetadata().isInterface()
                        && beanDefinition.getMetadata().getInterfaceNames().length == 1
                        && Annotation.class.getName().equals(beanDefinition.getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(),
                                DocumentScannerRegistrar.this.classLoader);
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            log.error("Could not load target class: {}", beanDefinition.getMetadata().getClassName(),
                                ex);
                        }
                    }
                    return true;
                }
                return false;
            }
        };
    }
}
