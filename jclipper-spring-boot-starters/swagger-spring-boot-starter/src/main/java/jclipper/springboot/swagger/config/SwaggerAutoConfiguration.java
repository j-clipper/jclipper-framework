package jclipper.springboot.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/13 09:18.
 */

@Configuration
//@ConditionalOnProperty(value = {SwaggerProperties.PREFIX+".enable"},havingValue = "true")
@EnableConfigurationProperties(SwaggerProperties.class)
@EnableOpenApi
public class SwaggerAutoConfiguration {

    @Autowired
    private SwaggerProperties properties;

    @Bean("defaultApi")
    @ConditionalOnMissingBean(name = "defaultApi")
    public Docket defaultApi() {
        Docket docket=new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title(properties.getTitle())
                // 文档描述
                .description(properties.getDescription())
                // URL
                .termsOfServiceUrl(properties.getUrl())
                // 文档版本
                .version(properties.getVersion())
                .build();
    }

    @Bean
    @Primary
    public DocumentationCache documentationCache(SwaggerProperties swaggerProperties) {
        return new DynamicDocumentationCache(swaggerProperties);
    }

}