package jclipper.springboot.exception;

import jclipper.common.exception.BadRequestException;
import jclipper.common.exception.SystemErrorException;
import jclipper.springboot.alert.base.AlertNotice;
import jclipper.springboot.alert.base.NoticeMessage;
import jclipper.springboot.exception.configuration.ErrorAlertProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.support.RpcUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/8 13:47.
 */
@Component
@Slf4j
public class DubboExceptionAlert {

    public static final String TRACE_KEY = "TraceId";

    public static final String TLOG_TRACE_KEY = "tlogTraceId";

    @Resource
    private AlertNotice alertNotice;

    @Value("${spring.application.name:unknown}")
    private String appName;

    @Resource
    private ErrorAlertProperties errorAlertProperties;

    public void alertError(Invoker<?> invoker, Invocation invocation, Throwable e) {
        if (e == null) {
            return;
        }


        String traceId = invocation.getAttachment(TLOG_TRACE_KEY);
        if (traceId == null) {
            traceId = invocation.getAttachment(TRACE_KEY);
        }
        if (alertNotice.enabled()) {
            NoticeMessage message = new NoticeMessage();
            boolean send = fillErrorInfo(message, e);
            if (!send) {
                return;
            }

            String serviceName = invoker.getInterface().getName();
            String methodName = RpcUtils.getMethodName(invocation);
            message.setAppName(appName);
            message.setEnv(errorAlertProperties.getEnv());
            message.setHost(invoker.getUrl().getAddress());
            message.setTime(LocalDateTime.now());
            message.setClient(RpcContext.getContext().getRemoteAddressString());
            message.setTraceId(traceId);
            message.setUrl(String.format("dubbo://%s.%s", serviceName, methodName));
            message.setMessage(e.getClass().getName());

            alertNotice.sendAsync(message);
        }

    }

    private boolean fillErrorInfo(NoticeMessage message, Throwable e) {
        message.setError(e.getMessage());
        message.setMessage(e.getClass().getName());
        String code = "error";
        if (e instanceof BadRequestException) {
            BadRequestException be = (BadRequestException) e;
            message.setCode(be.getError().getCode() + "");
            message.setMessage(be.getError().getName());
            if (!errorAlertProperties.isAlertWhenBizCode()) {
                if (be.getStatus() != null && be.getStatus().is5xxServerError()) {
                    return true;
                }
                return e instanceof SystemErrorException;
            }
            return true;
        }
        if (e instanceof RpcException) {
            code = getErrorCodeWhenRpcException((RpcException) e);
        }
        message.setCode(code);
        return true;
    }

    private String getErrorCodeWhenRpcException(RpcException e) {
        String code = "error";
        if (e.isTimeout()) {
            code = "timeoutError";
        }
        if (e.isBiz()) {
            code = "bisError";
        }
        if (e.isNetwork()) {
            code = "networkError";
        }
        if (e.isSerialization()) {
            code = "serializationError";
        }
        return code;

    }

}
