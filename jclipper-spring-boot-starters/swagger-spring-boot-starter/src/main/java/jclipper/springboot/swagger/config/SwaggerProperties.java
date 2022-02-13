package jclipper.springboot.swagger.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/13 09:38.
 */
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
@Getter
@Setter
@RefreshScope
public class SwaggerProperties {
    public static final String PREFIX = "jclipper.swagger";

    private boolean enable = false;
    private String basePackage="jclipper";
    private String title="";
    private String description="";
    private String version="1.0";
    private String url = "";
}
