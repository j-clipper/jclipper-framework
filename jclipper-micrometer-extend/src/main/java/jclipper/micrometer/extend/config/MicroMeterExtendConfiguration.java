package jclipper.micrometer.extend.config;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/10/21 11:18.
 */
@Configuration
@ComponentScan
public class MicroMeterExtendConfiguration {

    /**
     * 监控模块中统计结果里的时间分布的上限(单位毫秒)
     */
    private static final double[] MONITOR_LATENCY_BUCKETS = new double[]{10, 25, 50, 100, 250, 500, 1000, 2500, 5000, 10_000};


    @Bean
    @Primary
    public CollectorRegistry collectorRegistry() {
        return new CollectorRegistry(true);
    }

    @Bean(name = "consumerRequestHistogram")
    public Histogram consumerRequestHistogram(CollectorRegistry collectorRegistry) {
        return Histogram.build()
                //统计结果里的时间分布的上限(单位毫秒)
                .buckets(MONITOR_LATENCY_BUCKETS)
                .name("dubbo_consumer_request")
                .help("Dubbo consumer side request latency in milliseconds.")
                .labelNames("interface", "method", "status")
                .register(collectorRegistry);
    }


    @Bean(name = "providerResponseHistogram")
    public Histogram providerResponseHistogram(CollectorRegistry collectorRegistry) {
        return Histogram.build()
                //统计结果里的时间分布的上限(单位毫秒)
                .buckets(MONITOR_LATENCY_BUCKETS)
                .name("dubbo_provider_response")
                .help("Dubbo provider side response latency in milliseconds.")
                .labelNames("interface", "method", "status")
                .register(collectorRegistry);
    }
}

