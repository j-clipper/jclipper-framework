package jclipper.common.exception;

import jclipper.common.enums.CommonErrorCode;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/6/5 09:04.
 */
public class NotFoundException extends BusinessRequestException {
    public NotFoundException() {
        super(CommonErrorCode.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(CommonErrorCode.NOT_FOUND.getCode(), message);
    }

}
