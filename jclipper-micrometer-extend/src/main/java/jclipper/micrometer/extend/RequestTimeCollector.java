package jclipper.micrometer.extend;


import io.prometheus.client.Collector;

import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/10/21 11:54.
 */
public class RequestTimeCollector extends Collector {

    @Override
    public List<MetricFamilySamples> collect() {
        return null;
    }
}
