package jclipper.springboot.swagger.config;

import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/23 17:44.
 */
public class DynamicDocumentationCache extends DocumentationCache {

    private SwaggerProperties swaggerProperties;

    public DynamicDocumentationCache(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Override
    public Documentation documentationByGroup(String groupName) {
        if (!swaggerProperties.isEnable()) {
            return null;
        }
        return super.documentationByGroup(groupName);
    }
}
