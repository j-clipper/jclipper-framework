package jclipper.common.exception;

import jclipper.common.enums.BaseErrorCode;
import jclipper.common.enums.CommonErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 认证错误异常
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/27 13:47.
 */
public class UnauthorizedException extends BadRequestException {

    public UnauthorizedException() {
        this(CommonErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(BaseErrorCode error) {
        super(HttpStatus.UNAUTHORIZED, error);
    }
}
