package jclipper.common.utils;


import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * 描述: 列表处理工具类
 */
public class ListUtils {

    private ListUtils() {
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> newArrayList(T... elements) {
        ArrayList<T> list = new ArrayList<T>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

    /**
     * 描述: 将一个列表根据指定转换函数转换为另一个列表
     *
     * @param source
     * @param func
     * @return List<V>
     */
    public static <T, V> List<V> map(List<? extends T> source,
                                     Function<T, V> func) {
        return map(source, func, true);
    }

    public static <T, V> List<V> map(List<? extends T> source, Function<T, V> func,
                                     boolean removeNull) {
        List<V> result = newArrayList();
        if (CollectionUtils.isNotEmpty(source)) {
            for (T item : source) {
                V value = func.apply(item);
                if (value != null || !removeNull) {
                    result.add(value);
                }
            }
        }
        return result;
    }

    /**
     * 描述: 处理列表生成一个结果
     */
    public static <T, R> R reduce(List<? extends T> source,
                                  BiFunction<R, T, R> func, R init) {
        R result = init;
        if (CollectionUtils.isNotEmpty(source)) {
            for (T item : source) {
                result = func.apply(result, item);
            }
        }
        return result;
    }

    /**
     * 描述: 根据条件过滤列表
     *
     * @param source
     * @param predicate
     * @return List<T>
     */
    public static <T> List<T> filter(List<? extends T> source,
                                     Predicate<T> predicate) {
        List<T> result = newArrayList();
        if (CollectionUtils.isNotEmpty(source)) {
            for (T item : source) {
                if (predicate.test(item)) {
                    result.add(item);
                }
            }
        }
        return result;
    }

    /**
     * 描述: 获取集合中第一个匹配的元素
     *
     * @param source
     * @param predicate
     * @return T
     */
    public static <T> T firstMatch(List<? extends T> source,
                                   Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(source)) {
            return null;
        }
        T result = null;
        for (T item : source) {
            if (predicate.test(item)) {
                result = item;
                break;
            }
        }
        return result;
    }

    /**
     * 描述: 根据指定键函数去除包含在 target 列表中的元素
     *
     * @param source
     * @param target
     * @param keyFunc
     * @return List<T>
     */
    public static <T, K> List<T> diff(List<? extends T> source,
                                      List<? extends T> target, final Function<T, K> keyFunc) {
        final Set<K> keys = new HashSet<K>(ListUtils.map(target, keyFunc));
        return ListUtils.filter(source, t -> {
            K key = keyFunc.apply(t);
            return !keys.contains(key);
        });
    }

    /**
     * 描述: 将列表根据键函数转换为map
     *
     * @param source
     * @param keyFunc
     * @return Map<K, T>
     */
    public static <T, K> Map<K, T> toMap(List<? extends T> source,
                                         final Function<T, K> keyFunc) {
        Function<T, T> valFunc = identity();
        return ListUtils.toMap(source, keyFunc, valFunc);
    }


    public static <T> Function<T, T> identity() {
        return t -> t;
    }


    /**
     * 描述: 将列表根据键函数和值函数转换为map
     *
     * @param source
     * @param keyFunc
     * @param valFunc
     * @return Map<K, V>
     */
    public static <T, K, V> Map<K, V> toMap(List<? extends T> source,
                                            final Function<T, K> keyFunc, final Function<T, V> valFunc) {
        return ListUtils.reduce(source, (map, t) -> {
            K key = keyFunc.apply(t);
            if (key != null) {
                map.put(key, valFunc.apply(t));
            }
            return map;
        }, new HashMap<>());
    }

    /**
     * 描述: 将列表按给定键分组
     *
     * @return Map<K, List < T>>
     * @since v1.0.0
     */
    public static <T, K> Map<K, List<T>> group(List<? extends T> source,
                                               final Function<T, K> keyFunc) {
        Function<T, T> valFunc = identity();
        return group(source, keyFunc, valFunc);
    }

    /**
     * 描述: 将列表按给定键分组
     *
     * @return Map<K, List < V>>
     */
    public static <T, K, V> Map<K, List<V>> group(List<? extends T> source,
                                                  final Function<T, K> keyFunc, final Function<T, V> valFunc) {
        return ListUtils.reduce(source, (map, t) -> {
            K key = keyFunc.apply(t);
            if (key != null) {
                V val = valFunc.apply(t);
                List<V> list = map.get(key);
                if (CollectionUtils.isEmpty(list)) {
                    list = newArrayList();
                }
                list.add(val);
                map.put(key, list);
            }
            return map;
        }, new HashMap<>());
    }

    /**
     * 将列表转换为 Set
     *
     * @param source
     * @return
     */
    public static <T> Set<T> toSet(List<T> source) {
        return source == null ? Collections.emptySet() : new HashSet<>(source);
    }
}
