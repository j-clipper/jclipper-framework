package jclipper.common.exception;

import jclipper.common.enums.BaseErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 错误请求异常
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/27 12:01.
 */
@Getter
@Setter
public class BadRequestException extends RuntimeException {
    protected HttpStatus status;
    protected BaseErrorCode error;

    public BadRequestException(HttpStatus status, BaseErrorCode error) {
        super(error == null ? null : error.getName());
        this.status = status;
        this.error = error;
    }
}
