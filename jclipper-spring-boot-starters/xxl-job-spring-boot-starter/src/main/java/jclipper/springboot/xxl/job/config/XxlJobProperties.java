package jclipper.springboot.xxl.job.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/6/23 11:47.
 */
@ConfigurationProperties(prefix = XxlJobProperties.PREFIX)
@Getter
@Setter
public class XxlJobProperties {
    public static final String PREFIX = "xxl.job";

    /**
     * xxljob服务端地址，多个之间用英文逗号分隔
     */
    private String adminAddresses;

    /**
     * 连接到xxljob服务端的accessToken（当服务的设置了认证时需要）
     */
    private String accessToken;

    private Executor executor;


    @Data
    public static class Executor {
        /**
         * 要注册的执行器名称，建议设置为同 spring.application.name属性
         */
        private String appname;

        /**
         * 如果在可视化调度中心配置执行器为自动发现既可不要配置，推荐不配置
         */
        private String ip;
        /**
         * 调度器端口设置
         */
        private int port;
        /**
         * 执行器日志路径
         */
        private String logPath;

        /**
         * 日志保留时间，单位：天
         */
        private int logRetentionDays;
    }
}
