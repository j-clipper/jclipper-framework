package jclipper.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/6/29 21:00.
 */
public class MapBuilder<K, V> {
    private Map<K, V> map;

    public static <K, V> MapBuilder<K, V> newInstance() {
        return new MapBuilder<>();
    }

    public static <K, V> MapBuilder<K, V> of(Object... array) {
        return new MapBuilder<>(array);
    }

    public MapBuilder(Object... array) {
        this.map = convert(array);
    }

    public MapBuilder<K, V> add(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    public MapBuilder<K, V> add(Object... array) {
        this.map.putAll(convert(array));
        return this;
    }

    public MapBuilder<K, V> addIfAbsent(K key, V value) {
        this.map.putIfAbsent(key, value);
        return this;
    }

    public Map<K, V> build() {
        return this.map;
    }

    private Map<K, V> convert(Object... array) {
        if (array.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        if (array.length == 0) {
            return new HashMap<>(8);
        }
        int size = array.length / 2;
        Map<K, V> map = new HashMap<>(size);

        for (int i = 0; i < array.length; i += 2) {
            map.put((K) array[i], (V) array[i + 1]);
        }
        return map;
    }
}
