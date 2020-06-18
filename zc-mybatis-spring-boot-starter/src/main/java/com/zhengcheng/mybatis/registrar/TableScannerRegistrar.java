package com.zhengcheng.mybatis.registrar;

import com.zhengcheng.mybatis.annotation.TableName;
import com.zhengcheng.mybatis.metadata.TableInfo;
import com.zhengcheng.mybatis.metadata.TableInfoHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 自定义注解 @TableName 扫描注册器
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/29 20:07
 */
@Slf4j
public class TableScannerRegistrar implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private Environment environment;

    public TableScannerRegistrar() {
    }

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
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(TableName.class));
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents("com.gaodun");
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                String className = annotationMetadata.getClassName();
                try {
                    Class<?> beanClazz = Class.forName(className);
                    if (!beanClazz.isAnnotationPresent(TableName.class)) {
                        throw new RuntimeException("@TableName is required!");
                    }
                    TableInfo tableInfo = TableInfoHelper.initTableInfo(beanClazz);
                    log.info("Table: [{}]", tableInfo.getTableName());
                } catch (ClassNotFoundException e) {
                    log.error("Could not register target class: {}", annotationMetadata.getClassName(), e);
                }
            }
        }
    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (beanDefinition.getMetadata().isInterface()
                            && beanDefinition.getMetadata()
                            .getInterfaceNames().length == 1
                            && Annotation.class.getName().equals(beanDefinition
                            .getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(
                                    beanDefinition.getMetadata().getClassName(),
                                    TableScannerRegistrar.this.classLoader);
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            log.error("Could not load target class: {}", beanDefinition.getMetadata().getClassName(), ex);
                        }
                    }
                    return true;
                }
                return false;

            }
        };
    }
}
