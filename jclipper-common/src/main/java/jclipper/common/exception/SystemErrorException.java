package jclipper.common.exception;

import jclipper.common.enums.CommonErrorCode;
import jclipper.common.enums.CustomErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 系统异常
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/27 13:43.
 */
public class SystemErrorException extends BadRequestException {

    public SystemErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, new CustomErrorCode(CommonErrorCode.SYSTEM_ERROR.getCode(), message));
    }

    public SystemErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, CommonErrorCode.SYSTEM_ERROR);
    }
}
