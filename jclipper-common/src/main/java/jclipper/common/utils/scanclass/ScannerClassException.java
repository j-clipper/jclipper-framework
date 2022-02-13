package jclipper.common.utils.scanclass;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/7/1 13:56.
 */
public class ScannerClassException extends IllegalArgumentException {
    public ScannerClassException(String message, Exception e) {
        super(message, e);
    }
}
