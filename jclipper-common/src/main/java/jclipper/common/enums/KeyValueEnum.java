package jclipper.common.enums;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2022/2/17 00:43.
 */
public interface KeyValueEnum<K, V> extends BaseEnum<K, V> {
    /**
     *
     * @return
     */
    default K getKey() {
        return getCode();
    }

    /**
     *
     * @return
     */
    default V getValue() {
        return getName();
    }
}
