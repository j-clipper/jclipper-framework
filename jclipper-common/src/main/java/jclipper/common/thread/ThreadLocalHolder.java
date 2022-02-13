package jclipper.common.thread;

import jclipper.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class ThreadLocalHolder {

    private static final ThreadLocal<Map<String, Object>> RESOURCES = new InheritableThreadLocal<>();

    public static void bindResource(String key, Object value) {
        Map<String, Object> map = RESOURCES.get();
        if (null == map) {
            map = new HashMap<>(8);
            RESOURCES.set(map);
        }
        map.put(key, value);
    }

    public static boolean hasResource(String key) {
        return (getResource(key) != null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getResource(String key) {
        Map<String, Object> map = RESOURCES.get();
        if (null == map) {
            return null;
        }
        return (T) map.get(key);
    }

    public static <T> T getResource(String key, Function<String, T> function) {
        T value = getResource(key);
        if (value == null) {
            value = function.apply(key);
            if (value != null) {
                bindResource(key, value);
            }
        }
        return value;
    }

    public static void unBindAll() {
        Map<String, Object> map = RESOURCES.get();
        if (!CollectionUtils.isEmpty(map)) {
            map.clear();
            map = null;
            RESOURCES.remove();
        }
    }

    public static Object unBindResource(String key) {
        Map<String, Object> map = RESOURCES.get();
        if (map == null) {
            return null;
        }
        Object value = map.remove(key);

        if (map.isEmpty()) {
            RESOURCES.set(null);
        }
        if (value != null && log.isDebugEnabled()) {
            log.debug("Removed value [{}] for key [{}] from thread [{}]"
                    , key, value, Thread.currentThread().getName());
        }
        return value;
    }

    public static void unBindResource(String... keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        Map<String, Object> map = RESOURCES.get();
        if (map == null) {
            return;
        }
        for (String key : keys) {
            map.remove(key);
        }
        if (map.isEmpty()) {
            RESOURCES.set(null);
        }
    }

}
