package jclipper.micrometer.extend.config;

import jclipper.micrometer.extend.metrics.dubbo.DubboThreadMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/6/5 15:37.
 */
@Slf4j
@Component
public class AfterDubboLoadRunner implements ApplicationRunner {
    @Resource
    public MeterRegistry meterRegistry;
    /**
     * 会在服务启动完成后立即执行
     */
    @Override
    public void run(ApplicationArguments args) {
        new DubboThreadMetrics().bindTo(meterRegistry);
    }

}
