package jclipper.common.exception;

import jclipper.common.enums.CommonErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 权限不足异常
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/27 13:42.
 */
public class ForbiddenException extends BadRequestException{
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, CommonErrorCode.FORBIDDEN);
    }
}
