package jclipper.webapp.base.configuration;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

/**
 * 动态更新log4j2配置：
 * <p>
 *     1、从 dataId=log4j2-spring.xml 中载入log4j2的配置；
 *     2、监听 log4j2-spring.xml 的变化并重新刷新 log4j2的配置；
 * </p>
 * 如果不想使用此功能，请设置jvm启动参数 -Dnacos.log4j2.dynamic.enabled=false ，或配置Spring参数 nacos.log4j2.dynamic.enabled=false
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/5/19 09:11.
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnProperty(prefix = "nacos.log4j2.dynamic", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
@AutoConfigureAfter(NacosConfigManager.class)
public class NacosDynamicLog4j2Configuration implements InitializingBean {

    public static final String LOG4J2_DATA_ID = "log4j2-spring.xml";

    @Resource
    private NacosConfigManager nacosConfigManager;
    @Resource
    private NacosConfigProperties configProperties;


    @PostConstruct
    public void init() throws NacosException {
        String xmlContent = nacosConfigManager.getConfigService().getConfig(LOG4J2_DATA_ID, configProperties.getGroup(), 5000);
        if (Strings.isNullOrEmpty(xmlContent)) {
            return;
        }
        refreshLog4j2Config(xmlContent);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        nacosConfigManager.getConfigService().addListener(LOG4J2_DATA_ID, configProperties.getGroup(),
                new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        refreshLog4j2Config(configInfo);
                    }
                });
    }

    public static void refreshLog4j2Config(String xmlContent) {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        try (InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8))) {
            ConfigurationSource source = new ConfigurationSource(inputStream);
            Configuration configuration = new XmlConfigurationFactory().getConfiguration(context, source);
            context.setConfiguration(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

