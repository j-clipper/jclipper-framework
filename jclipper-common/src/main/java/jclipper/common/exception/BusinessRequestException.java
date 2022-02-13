package jclipper.common.exception;

import jclipper.common.enums.BaseErrorCode;
import jclipper.common.enums.CustomErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 业务异常
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/27 13:39.
 */
public class BusinessRequestException extends BadRequestException {
    public BusinessRequestException(HttpStatus status, Integer code, String message) {
        super(status, new CustomErrorCode(code, message));
    }

    public BusinessRequestException(Integer code, String message) {
        super(HttpStatus.BAD_REQUEST, new CustomErrorCode(code, message));
    }

    public BusinessRequestException(BaseErrorCode error) {
        super(HttpStatus.BAD_REQUEST, error);
    }
}
