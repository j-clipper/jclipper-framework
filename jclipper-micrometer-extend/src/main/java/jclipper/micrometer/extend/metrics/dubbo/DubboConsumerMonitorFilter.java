package jclipper.micrometer.extend.metrics.dubbo;

import jclipper.micrometer.extend.config.MicrometerInternalSpringUtil;
import io.prometheus.client.Histogram;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;

//

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/10/21 11:23.
 */
@Slf4j
@Activate(group = {CONSUMER})
public class DubboConsumerMonitorFilter extends AbstractDubboMonitorFilter {


    @Override
    Histogram histogram() {
        return (Histogram) MicrometerInternalSpringUtil.getBean("consumerRequestHistogram");
    }

    @Override
    void afterInvoke(String[] labels, long duration, boolean isError) {
        Histogram histogram = histogram();
        if (histogram == null) {
            log.warn("histogram is null");
            return;
        }
        histogram.labels(labels).observe(duration);
    }
}
