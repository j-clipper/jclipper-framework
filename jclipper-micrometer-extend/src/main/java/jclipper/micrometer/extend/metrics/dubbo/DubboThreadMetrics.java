package jclipper.micrometer.extend.metrics.dubbo;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.threadpool.manager.DefaultExecutorRepository;
import org.apache.dubbo.common.threadpool.manager.ExecutorRepository;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Dubbo线程池指标
 *
 * @author yinjihuan
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @see <a href="https://netsecurity.51cto.com/art/202102/645157.htm">https://netsecurity.51cto.com/art/202102/645157.htm</a>
 * @since 2021/10/21 10:55.
 */
@Slf4j
public class DubboThreadMetrics implements MeterBinder {

    @Override
    public void bindTo(MeterRegistry meterRegistry) {

        ExecutorRepository executorRepository = ExtensionLoader.getExtensionLoader(ExecutorRepository.class).getDefaultExtension();

        if (executorRepository instanceof DefaultExecutorRepository) {
            DefaultExecutorRepository defaultExecutorRepository = (DefaultExecutorRepository) executorRepository;

            //String componentKey = EXECUTOR_SERVICE_COMPONENT_KEY;
            //        if (CONSUMER_SIDE.equalsIgnoreCase(url.getParameter(SIDE_KEY))) {
            //            componentKey = CONSUMER_SIDE;
            //        }
            // data的key是固定的，要么是 EXECUTOR_SERVICE_COMPONENT_KEY 要么是 CONSUMER_SIDE
// 反射读取data字段
            ConcurrentMap<String, ConcurrentMap<Integer, ExecutorService>> data = getData(defaultExecutorRepository);
            if (data == null) {
                return;
            }

            //provider
            ConcurrentMap<Integer, ExecutorService> executors = data.get(CommonConstants.EXECUTOR_SERVICE_COMPONENT_KEY);
            if (executors != null) {

                executors.forEach((port, executor) -> {
                    if (executor instanceof ThreadPoolExecutor) {
                        ThreadPoolExecutor tp = (ThreadPoolExecutor) executor;
                        Gauge.builder("dubbo.thread.pool.core.size", tp, ThreadPoolExecutor::getCorePoolSize)
                                .description("核心线程数")
                                .baseUnit("threads")
                                .register(meterRegistry);
                        Gauge.builder("dubbo.thread.pool.largest.size", tp, ThreadPoolExecutor::getLargestPoolSize)
                                .description("历史最高线程数")
                                .baseUnit("threads")
                                .register(meterRegistry);
                        Gauge.builder("dubbo.thread.pool.max.size", tp, ThreadPoolExecutor::getMaximumPoolSize)
                                .description("最大线程数")
                                .baseUnit("threads")
                                .register(meterRegistry);
                        Gauge.builder("dubbo.thread.pool.active.size", tp, ThreadPoolExecutor::getActiveCount)
                                .description("活跃线程数")
                                .baseUnit("threads")
                                .register(meterRegistry);
                        Gauge.builder("dubbo.thread.pool.thread.count", tp, ThreadPoolExecutor::getPoolSize)
                                .description("当前线程数")
                                .baseUnit("threads")
                                .register(meterRegistry);
                        Gauge.builder("dubbo.thread.pool.queue.size", tp, e -> e.getQueue().size())
                                .description("队列大小")
                                .baseUnit("threads")
                                .register(meterRegistry);
                        Gauge.builder("dubbo.thread.pool.taskCount", tp, ThreadPoolExecutor::getTaskCount)
                                .description("任务总量")
                                .baseUnit("threads")
                                .register(meterRegistry);
                        Gauge.builder("dubbo.thread.pool.completedTaskCount", tp, ThreadPoolExecutor::getCompletedTaskCount)
                                .description("已完成的任务量")
                                .baseUnit("threads")
                                .register(meterRegistry);

                    }
                });
            }

        } else {
            log.warn("unchecked thread pool implement. Plz contact developer.");
        }
    }

    private ConcurrentMap<String, ConcurrentMap<Integer, ExecutorService>> getData(DefaultExecutorRepository defaultExecutorRepository) {
        Class clazz =defaultExecutorRepository.getClass();
        Field[] fs = clazz.getDeclaredFields();
        for (Field field : fs) {
            /**
             如果成员变量是private的,则需要设置accessible为true
             如果成员变量是public的,则不需要设置
             **/
            if("data".equals(field.getName())) {
                field.setAccessible(true);
                try {
                    return (ConcurrentMap<String, ConcurrentMap<Integer, ExecutorService>>)field.get(defaultExecutorRepository);
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
