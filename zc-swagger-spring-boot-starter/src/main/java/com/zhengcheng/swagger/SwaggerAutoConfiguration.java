package com.zhengcheng.swagger;

import com.zhengcheng.swagger.properties.SwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerAutoConfiguration
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/2 15:54
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.swagger", name = "enable", havingValue = "true")
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@EnableConfigurationProperties({SwaggerProperties.class})
public class SwaggerAutoConfiguration {

    public SwaggerAutoConfiguration() {
        log.info("------ Knife4j-3.0.3 自动配置  ---------------------------------------");
    }

    @Bean(value = "knife4jRestApi")
    @Order(value = 1)
    public Docket createRestApi(SwaggerProperties swaggerProperties) {
        return new Docket(DocumentationType.OAS_30)
                .groupName(swaggerProperties.getGroupName())
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .enable(swaggerProperties.getEnable() != null && swaggerProperties.getEnable());
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .build();
    }

}
