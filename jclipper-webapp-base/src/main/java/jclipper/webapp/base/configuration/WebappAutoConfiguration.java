package jclipper.webapp.base.configuration;

import jclipper.webapp.base.AfterServiceStartedRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/6/9 16:05.
 */
@Configuration
@Import({NacosCustomDiscoveryClientConfiguration.class, NacosDynamicLog4j2Configuration.class})
public class WebappAutoConfiguration {
    @Bean
    public AfterServiceStartedRunner afterServiceStartedRunner() {
        return new AfterServiceStartedRunner();
    }
}
