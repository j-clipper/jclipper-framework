package jclipper.common.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/8/17 09:47.
 */
public class UnsafeUtils {

    public static Unsafe UNSAFE = getUnsafe();

    public static Unsafe unsafe() {
        return UNSAFE;
    }

    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        } catch (SecurityException se) {
            try {
                return java.security.AccessController.doPrivileged
                        ((PrivilegedExceptionAction<Unsafe>) () -> {
                            Field f = Unsafe.class.getDeclaredField("theUnsafe");
                            f.setAccessible(true);
                            return (Unsafe) f.get(null);
                        });
            } catch (java.security.PrivilegedActionException e) {
                throw new RuntimeException("Could not initialize intrinsics",
                        e.getCause());
            }
        }
    }
}
