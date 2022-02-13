package jclipper.micrometer.extend.metrics.dubbo;

import com.alibaba.fastjson.JSON;
import io.prometheus.client.Histogram;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.support.RpcUtils;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/10/25 10:14.
 */
@Slf4j
public abstract class AbstractDubboMonitorFilter implements Filter {

    abstract Histogram histogram();


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String serviceName = invoker.getInterface().getName();
        String methodName = RpcUtils.getMethodName(invocation);
        long start = System.currentTimeMillis();
        String status = "success";
        boolean success = true;
        try {
            // proceed invocation chain
            Result result = invoker.invoke(invocation);

            if (result.getException() != null) {
                status = result.getException().getClass().getSimpleName();
            }

            return result;
        } catch (RpcException e) {

            status = "error";
            if (e.isTimeout()) {
                status = "timeoutError";
            }
            if (e.isBiz()) {
                status = "bisError";
            }
            if (e.isNetwork()) {
                status = "networkError";
            }
            if (e.isSerialization()) {
                status = "serializationError";
            }
            success = false;
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - start;
            String[] labels = new String[]{serviceName, methodName, status};
            if (log.isDebugEnabled()) {
                log.debug("{} spent {}", JSON.toJSONString(labels), duration);
            }
            afterInvoke(labels, duration, success);
        }
    }

    abstract void afterInvoke(String[] labels, long duration, boolean isError);

}
