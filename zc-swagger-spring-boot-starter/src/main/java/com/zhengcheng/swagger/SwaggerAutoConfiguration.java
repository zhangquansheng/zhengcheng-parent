package com.zhengcheng.swagger;

import com.zhengcheng.swagger.properties.SwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
@EnableConfigurationProperties({SwaggerProperties.class})
public class SwaggerAutoConfiguration {

    public SwaggerAutoConfiguration() {
        log.info("------ Swagger 2.8.0 自动配置  ---------------------------------------");
    }

    @Bean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {
        List<Parameter> operationParameters = new ArrayList<>();
        operationParameters.add(new ParameterBuilder().name("access_token").description("访问令牌").modelRef(new ModelRef("String")).parameterType("query").required(false).build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerProperties.getGroupName())
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                //配置鉴权信息
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .globalOperationParameters(operationParameters)
                .enable(swaggerProperties.getEnable() == null ? false : swaggerProperties.getEnable());
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

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    private ArrayList securitySchemes() {
        return new ArrayList(Collections.singleton(new ApiKey("Bearer", "Authorization", "header")));
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList(
                Collections.singleton(SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build())
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList(Collections.singleton(new SecurityReference("Bearer", authorizationScopes)));
    }
}
