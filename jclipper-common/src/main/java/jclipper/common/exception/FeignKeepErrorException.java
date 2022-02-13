package jclipper.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;

/**
 * Feign调用异常
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/28 14:20.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeignKeepErrorException extends RuntimeException{
    private int httpCode;
    private String error;
    private Map<String, Collection<String>> headers;
}
