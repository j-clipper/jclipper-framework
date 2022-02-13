package jclipper.springboot.exception.configuration;

import jclipper.springboot.alert.base.AlertNotice;
import jclipper.springboot.alert.base.NoticeConfig;
import jclipper.springboot.alert.core.impl.ComposeAlertNotice;
import jclipper.springboot.alert.core.impl.DingTalkMarkdownAlertNotice;
import jclipper.springboot.alert.core.impl.WorkWechatMarkdownAlertNotice;
import jclipper.springboot.alert.core.impl.error.DingTalkMarkdownErrorAlertNotice;
import jclipper.springboot.alert.core.impl.error.WorkWechatMarkdownErrorAlertNotice;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/6/8 14:20.
 */
@ComponentScan(basePackages = "jclipper.springboot.exception")
@Configuration
@EnableConfigurationProperties(ErrorAlertProperties.class)
public class GlobalExceptionHandlerAutoConfiguration {

    @Component("workWechatErrorAlertConfig")
    @RefreshScope
    @ConditionalOnProperty(prefix = WorkWechatErrorAlertConfig.PREFIX, name = "enable", havingValue = "true")
    @ConfigurationProperties(prefix = WorkWechatErrorAlertConfig.PREFIX)
    @Data
    public static class WorkWechatErrorAlertConfig extends NoticeConfig {
        public static final String PREFIX = "jclipper.alert.work-wechat";
        private String url = WorkWechatMarkdownAlertNotice.URL;

    }

    @Component("dingTalkErrorAlertConfig")
    @RefreshScope
    @ConditionalOnProperty(prefix = DingTalkErrorAlertConfig.PREFIX, name = "enable", havingValue = "true")
    @ConfigurationProperties(prefix = DingTalkErrorAlertConfig.PREFIX)
    @Data
    public static class DingTalkErrorAlertConfig extends NoticeConfig {
        public static final String PREFIX = "jclipper.alert.dingtalk";

        private String url = DingTalkMarkdownAlertNotice.URL;

    }

    @ConditionalOnBean(name = "dingTalkErrorAlertConfig")
    @Bean("dingTalkMarkdownErrorAlert")
    @ConditionalOnMissingBean(name = "dingTalkMarkdownErrorAlert")
    public AlertNotice dingTalkMarkdownErrorAlert(DingTalkErrorAlertConfig config) {
        return new DingTalkMarkdownErrorAlertNotice(config);
    }

    @ConditionalOnBean(name = "workWechatErrorAlertConfig")
    @Bean("workWechatMarkdownErrorAlert")
    @ConditionalOnMissingBean(name = "workWechatMarkdownErrorAlert")
    public AlertNotice workWechatMarkdownErrorAlert(WorkWechatErrorAlertConfig config) {
        return new WorkWechatMarkdownErrorAlertNotice(config);
    }


    @Bean
    @Primary
    public AlertNotice composeErrorAlert(List<AlertNotice> alerts) {
        return new ComposeAlertNotice(alerts);
    }

}
