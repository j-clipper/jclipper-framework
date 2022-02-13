package jclipper.common.exception;

import jclipper.common.enums.CommonErrorCode;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/7/1 11:55.
 */
public class ResourceLockedException extends BusinessRequestException {
    public ResourceLockedException() {
        super(CommonErrorCode.RESOURCE_LOCKED);
    }
}
