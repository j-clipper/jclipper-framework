package jclipper.common.exception;

import jclipper.common.enums.CommonErrorCode;
import jclipper.common.enums.CustomErrorCode;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/11 21:13.
 */
public class ParamValiateException extends BusinessRequestException{

    public ParamValiateException() {
        this(CommonErrorCode.REQUEST_PARAM_ERROR.getName());
    }
    public ParamValiateException(String message) {
        super(new CustomErrorCode(CommonErrorCode.REQUEST_PARAM_ERROR.getCode(),message));
    }
}
