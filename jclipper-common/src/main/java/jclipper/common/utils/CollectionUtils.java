package jclipper.common.utils;


import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Arjen Poutsma
 * @author wf2311
 */
public final class CollectionUtils {


    /**
     * Default load factor for {@link HashMap}/{@link LinkedHashMap} variants.
     *
     * @see #newHashMap(int)
     * @see #newLinkedHashMap(int)
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;


    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * Instantiate a new {@link HashMap} with an initial capacity
     * that can accommodate the specified number of elements without
     * any immediate resize/rehash operations to be expected.
     * <p>This differs from the regular {@link HashMap} constructor
     * which takes an initial capacity relative to a load factor
     * but is effectively aligned with the JDK's
     * {@link ConcurrentHashMap#ConcurrentHashMap(int)}.
     *
     * @param expectedSize the expected number of elements (with a corresponding
     *                     capacity to be derived so that no resize/rehash operations are needed)
     * @see #newLinkedHashMap(int)
     * @since 5.3
     */
    public static <K, V> HashMap<K, V> newHashMap(int expectedSize) {
        return new HashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Instantiate a new {@link LinkedHashMap} with an initial capacity
     * that can accommodate the specified number of elements without
     * any immediate resize/rehash operations to be expected.
     * <p>This differs from the regular {@link LinkedHashMap} constructor
     * which takes an initial capacity relative to a load factor but is
     * aligned with Spring's own {@link LinkedCaseInsensitiveMap} and
     * {@link LinkedMultiValueMap} constructor semantics as of 5.3.
     *
     * @param expectedSize the expected number of elements (with a corresponding
     *                     capacity to be derived so that no resize/rehash operations are needed)
     * @see #newHashMap(int)
     * @since 5.3
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int expectedSize) {
        return new LinkedHashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * Convert the supplied array into a List. A primitive array gets converted
     * into a List of the appropriate wrapper type.
     * <p><b>NOTE:</b> Generally prefer the standard {@link Arrays#asList} method.
     * This {@code arrayToList} method is just meant to deal with an incoming Object
     * value that might be an {@code Object[]} or a primitive array at runtime.
     * <p>A {@code null} source value will be converted to an empty List.
     *
     * @param source the (potentially primitive) array
     * @return the converted List result
     * @see ObjectUtils#toObjectArray(Object)
     * @see Arrays#asList(Object[])
     */
    public static List<?> arrayToList(@Nullable Object source) {
        return Arrays.asList(ObjectUtils.toObjectArray(source));
    }

    /**
     * Merge the given array into the given Collection.
     *
     * @param array      the array to merge (may be {@code null})
     * @param collection the target Collection to merge the array into
     */
    @SuppressWarnings("unchecked")
    public static <E> void mergeArrayIntoCollection(@Nullable Object array, Collection<E> collection) {
        Object[] arr = ObjectUtils.toObjectArray(array);
        for (Object elem : arr) {
            collection.add((E) elem);
        }
    }

    /**
     * Merge the given Properties instance into the given Map,
     * copying all properties (key-value pairs) over.
     * <p>Uses {@code Properties.propertyNames()} to even catch
     * default properties linked into the original Properties instance.
     *
     * @param props the Properties instance to merge (may be {@code null})
     * @param map   the target Map to merge the properties into
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void mergePropertiesIntoMap(@Nullable Properties props, Map<K, V> map) {
        if (props != null) {
            for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements(); ) {
                String key = (String) en.nextElement();
                Object value = props.get(key);
                if (value == null) {
                    // Allow for defaults fallback or potentially overridden accessor...
                    value = props.getProperty(key);
                }
                map.put((K) key, (V) value);
            }
        }
    }


    /**
     * Check whether the given Iterator contains the given element.
     *
     * @param iterator the Iterator to check
     * @param element  the element to look for
     * @return {@code true} if found, {@code false} otherwise
     */
    public static boolean contains(@Nullable Iterator<?> iterator, Object element) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                Object candidate = iterator.next();
                if (ObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the given Enumeration contains the given element.
     *
     * @param enumeration the Enumeration to check
     * @param element     the element to look for
     * @return {@code true} if found, {@code false} otherwise
     */
    public static boolean contains(@Nullable Enumeration<?> enumeration, Object element) {
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Object candidate = enumeration.nextElement();
                if (ObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the given Collection contains the given element instance.
     * <p>Enforces the given instance to be present, rather than returning
     * {@code true} for an equal element as well.
     *
     * @param collection the Collection to check
     * @param element    the element to look for
     * @return {@code true} if found, {@code false} otherwise
     */
    public static boolean containsInstance(@Nullable Collection<?> collection, Object element) {
        if (collection != null) {
            for (Object candidate : collection) {
                if (candidate == element) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return {@code true} if any element in '{@code candidates}' is
     * contained in '{@code source}'; otherwise returns {@code false}.
     *
     * @param source     the source Collection
     * @param candidates the candidates to search for
     * @return whether any of the candidates has been found
     */
    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        return findFirstMatch(source, candidates) != null;
    }

    /**
     * Return the first element in '{@code candidates}' that is contained in
     * '{@code source}'. If no element in '{@code candidates}' is present in
     * '{@code source}' returns {@code null}. Iteration order is
     * {@link Collection} implementation specific.
     *
     * @param source     the source Collection
     * @param candidates the candidates to search for
     * @return the first present object, or {@code null} if not found
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return null;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return (E) candidate;
            }
        }
        return null;
    }

    /**
     * Find a single value of the given type in the given Collection.
     *
     * @param collection the Collection to search
     * @param type       the type to look for
     * @return a value of the given type found if there is a clear match,
     * or {@code null} if none or more than one such value found
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T findValueOfType(Collection<?> collection, @Nullable Class<T> type) {
        if (isEmpty(collection)) {
            return null;
        }
        T value = null;
        for (Object element : collection) {
            if (type == null || type.isInstance(element)) {
                if (value != null) {
                    // More than one value found... no clear single value.
                    return null;
                }
                value = (T) element;
            }
        }
        return value;
    }

    /**
     * Find a single value of one of the given types in the given Collection:
     * searching the Collection for a value of the first type, then
     * searching for a value of the second type, etc.
     *
     * @param collection the collection to search
     * @param types      the types to look for, in prioritized order
     * @return a value of one of the given types found if there is a clear match,
     * or {@code null} if none or more than one such value found
     */
    @Nullable
    public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
        if (isEmpty(collection) || ObjectUtils.isEmpty(types)) {
            return null;
        }
        for (Class<?> type : types) {
            Object value = findValueOfType(collection, type);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    /**
     * Determine whether the given Collection only contains a single unique object.
     *
     * @param collection the Collection to check
     * @return {@code true} if the collection contains a single reference or
     * multiple references to the same instance, {@code false} otherwise
     */
    public static boolean hasUniqueObject(Collection<?> collection) {
        if (isEmpty(collection)) {
            return false;
        }
        boolean hasCandidate = false;
        Object candidate = null;
        for (Object elem : collection) {
            if (!hasCandidate) {
                hasCandidate = true;
                candidate = elem;
            } else if (candidate != elem) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find the common element type of the given Collection, if any.
     *
     * @param collection the Collection to check
     * @return the common element type, or {@code null} if no clear
     * common type has been found (or the collection was empty)
     */
    @Nullable
    public static Class<?> findCommonElementType(Collection<?> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        Class<?> candidate = null;
        for (Object val : collection) {
            if (val != null) {
                if (candidate == null) {
                    candidate = val.getClass();
                } else if (candidate != val.getClass()) {
                    return null;
                }
            }
        }
        return candidate;
    }

    /**
     * Retrieve the first element of the given Set, using {@link SortedSet#first()}
     * or otherwise using the iterator.
     *
     * @param set the Set to check (may be {@code null} or empty)
     * @return the first element, or {@code null} if none
     * @see SortedSet
     * @see LinkedHashMap#keySet()
     * @see LinkedHashSet
     * @since 5.2.3
     */
    @Nullable
    public static <T> T firstElement(@Nullable Set<T> set) {
        if (isEmpty(set)) {
            return null;
        }
        if (set instanceof SortedSet) {
            return ((SortedSet<T>) set).first();
        }

        Iterator<T> it = set.iterator();
        T first = null;
        if (it.hasNext()) {
            first = it.next();
        }
        return first;
    }

    /**
     * Retrieve the first element of the given List, accessing the zero index.
     *
     * @param list the List to check (may be {@code null} or empty)
     * @return the first element, or {@code null} if none
     * @since 5.2.3
     */
    @Nullable
    public static <T> T firstElement(@Nullable List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Retrieve the last element of the given Set, using {@link SortedSet#last()}
     * or otherwise iterating over all elements (assuming a linked set).
     *
     * @param set the Set to check (may be {@code null} or empty)
     * @return the last element, or {@code null} if none
     * @see SortedSet
     * @see LinkedHashMap#keySet()
     * @see LinkedHashSet
     * @since 5.0.3
     */
    @Nullable
    public static <T> T lastElement(@Nullable Set<T> set) {
        if (isEmpty(set)) {
            return null;
        }
        if (set instanceof SortedSet) {
            return ((SortedSet<T>) set).last();
        }

        // Full iteration necessary...
        Iterator<T> it = set.iterator();
        T last = null;
        while (it.hasNext()) {
            last = it.next();
        }
        return last;
    }

    /**
     * Retrieve the last element of the given List, accessing the highest index.
     *
     * @param list the List to check (may be {@code null} or empty)
     * @return the last element, or {@code null} if none
     * @since 5.0.3
     */
    @Nullable
    public static <T> T lastElement(@Nullable List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * Marshal the elements from the given enumeration into an array of the given type.
     * Enumeration elements must be assignable to the type of the given array. The array
     * returned will be a different instance than the array given.
     */
    public static <A, E extends A> A[] toArray(Enumeration<E> enumeration, A[] array) {
        ArrayList<A> elements = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            elements.add(enumeration.nextElement());
        }
        return elements.toArray(array);
    }

    /**
     * Adapt an {@link Enumeration} to an {@link Iterator}.
     *
     * @param enumeration the original {@code Enumeration}
     * @return the adapted {@code Iterator}
     */
    public static <E> Iterator<E> toIterator(@Nullable Enumeration<E> enumeration) {
        return (enumeration != null ? new EnumerationIterator<>(enumeration) : Collections.emptyIterator());
    }

    /**
     * Adapt a {@code Map<K, List<V>>} to an {@code MultiValueMap<K, V>}.
     *
     * @param targetMap the original map
     * @return the adapted multi-value map (wrapping the original map)
     * @since 3.1
     */
    public static <K, V> MultiValueMap<K, V> toMultiValueMap(Map<K, List<V>> targetMap) {
        return new MultiValueMapAdapter<>(targetMap);
    }

    /**
     * Return an unmodifiable view of the specified multi-value map.
     *
     * @param targetMap the map for which an unmodifiable view is to be returned.
     * @return an unmodifiable view of the specified multi-value map
     * @since 3.1
     */
    @SuppressWarnings("unchecked")
    public static <K, V> MultiValueMap<K, V> unmodifiableMultiValueMap(
            MultiValueMap<? extends K, ? extends V> targetMap) {

        Assert.notNull(targetMap, "'targetMap' must not be null");
        Map<K, List<V>> result = newLinkedHashMap(targetMap.size());
        targetMap.forEach((key, value) -> {
            List<? extends V> values = Collections.unmodifiableList(value);
            result.put(key, (List<V>) values);
        });
        Map<K, List<V>> unmodifiableMap = Collections.unmodifiableMap(result);
        return toMultiValueMap(unmodifiableMap);
    }


    /**
     * Iterator wrapping an Enumeration.
     */
    private static class EnumerationIterator<E> implements Iterator<E> {

        private final Enumeration<E> enumeration;

        public EnumerationIterator(Enumeration<E> enumeration) {
            this.enumeration = enumeration;
        }

        @Override
        public boolean hasNext() {
            return this.enumeration.hasMoreElements();
        }

        @Override
        public E next() {
            return this.enumeration.nextElement();
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Not supported");
        }
    }

    private CollectionUtils() {
    }

    /**
     * 将List按指定大小拆分
     *
     * @param list
     * @param subSize 每个子List的大小
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, final int subSize) {
        List<List<T>> parts = new ArrayList<>();
        final int size = list.size();
        for (int i = 0; i < size; i += subSize) {
            parts.add(new ArrayList<>(list.subList(i, Math.min(size, i + subSize)))
            );
        }
        return parts;
    }


    /**
     * 判断集合不为空
     */
    public static <E> boolean isNotEmpty(Collection<E> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isEmpty(boolean[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(byte[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(char[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(short[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(int[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(float[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(double[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(long[] array) {
        return (array == null || array.length == 0);
    }

    public static <T> boolean isEmpty(T[] array) {
        return (array == null || array.length == 0);
    }


    public static boolean isNotEmpty(boolean[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(char[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(short[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(int[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(float[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(double[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(long[] array) {
        return !isEmpty(array);
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 将List转为Map
     */
    public static <K, T> Map<K, T> toMap(Collection<T> list, Function<T, K> groupFeature) {
        return toMap(list, groupFeature, Function.identity());
    }

    /**
     * 将Collection转为Map
     */
    public static <K, V, T> Map<K, V> toMap(Collection<T> list, Function<T, K> groupFeature, Function<T, V> mapping) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyMap();
        }
        return list.stream()
                //过滤掉为空的情况
                .filter(notNullResultFunction(groupFeature))
                .collect(Collectors.toMap(groupFeature, mapping));
    }

    /**
     * 将列表按指定的特征分组，并用map存放
     */
    public static <K, T> Map<K, List<T>> getMapGroupByFeature(List<T> list, Function<T, K> groupFeature) {
        return getMapGroupByFeature(list, groupFeature, Function.identity());
    }

    /**
     * 将列表按指定的特征条件分组，并用map存放
     */
    public static <K, V, T> Map<K, List<V>> getMapGroupByFeature(List<T> list, Function<T, K> groupFeature, Function<T, V> mappingToList) {
        Map<K, List<V>> map = list.stream()
                //过滤掉为空的情况
                .filter(notNullResultFunction(groupFeature))
                .collect(groupingBy(groupFeature, mapping(mappingToList, toList())));
        List<T> nullGroup = list.stream().filter(nullResultFunction(groupFeature)).collect(toList());
        //如果存在特征条件为的数据，加入map
        if (isNotEmpty(nullGroup)) {
            map.put(null, nullGroup.stream().map(mappingToList).collect(toList()));
        }
        return map;
    }

    /**
     * 得到列表的指定分组特征列表
     *
     * @param list         列表
     * @param groupFeature 分组特征
     * @return
     */
    public static <K, T> List<K> getGroupFeatureList(List<T> list, Function<T, K> groupFeature) {
        List<K> ks = list.stream().filter(notNullResultFunction(groupFeature))
                .map(groupFeature).distinct().sorted().collect(toList());
        boolean hasNullFeature = list.stream().anyMatch(nullResultFunction(groupFeature));
        if (hasNullFeature) {
            ks.add(null);
        }
        return ks;
    }

    /**
     * 得到列表的指定分组特征列表
     *
     * @param list         列表
     * @param groupFeature 分组特征
     * @param comparator   排序依据
     * @return
     */
    public static <K, T> List<K> getGroupFeatureList(List<T> list, Function<T, K> groupFeature, Comparator<K> comparator) {
        Stream<K> stream = list.stream().filter(notNullResultFunction(groupFeature))
                .map(groupFeature).distinct();
        if (comparator != null) {
            stream = stream.sorted(comparator);
        }
        List<K> ks = stream.collect(toList());
        boolean hasNullFeature = list.stream().anyMatch(nullResultFunction(groupFeature));
        if (hasNullFeature) {
            ks.add(null);
        }
        return ks;
    }

    /**
     * 得到列表中满足指定特征条件的子列表
     */
    public static <K, T> List<T> getSubListByGroupFeature(List<T> list, Function<T, K> groupFeature, K k) {
        Map<K, List<T>> map = getMapGroupByFeature(list, groupFeature);
        return map.get(k);
    }

    /**
     * 按照特征条件排序，得到列表中满足指定条件的第一个子列表
     */
    public static <K, T> List<T> getFirstSubListByGroupFeature(List<T> list, Function<T, K> groupFeature, boolean asc) {
        List<K> keys = getGroupFeatureList(list, groupFeature);
        K k;
        if (asc) {
            k = keys.get(0);
        } else {
            k = keys.get(keys.size() - 1);
        }
        return getSubListByGroupFeature(list, groupFeature, k);
    }

    /**
     * List类型转换
     * <pre>
     *     List<Integer> integerList = convertList(Arrays.asList("1","2","3"), s -> Integer.parseInt(s));
     * </pre>
     *
     * @param source 源list
     * @param func   转换函数
     * @param <T>    源list类型
     * @param <U>    目标list类型
     */
    public static <T, U> List<U> convertList(List<T> source, Function<T, U> func) {
        return source.stream().map(func).collect(toList());
    }

    /**
     * List转换为数组
     * <pre>
     *   Double[] doubleArr = convertArray(Arrays.asList("1","2","3"), Double::parseDouble, Double[]::new);
     * </pre>
     *
     * @param source    源list
     * @param func      转换函数
     * @param generator 函数：产生一个数组
     * @param <T>       源list类型
     * @param <U>       目标list类型
     * @return
     */
    public static <T, U> U[] convertArray(T[] source, Function<T, U> func, IntFunction<U[]> generator) {
        return Arrays.stream(source).map(func).toArray(generator);
    }

    /**
     * 将Map<K, V> 转为 Map<V,List<K>>
     * <br/>
     * 将
     * <pre>
     * Map<String, String> values = new HashMap<String, String>();
     * values.put("aa", "20");
     * values.put("bb", "30");
     * values.put("cc", "20");
     * values.put("dd", "45");
     * values.put("ee", "35");
     * values.put("ff", "35");
     * values.put("gg", "20");
     * </pre>
     * 转为
     * <pre>
     *      "20" -> ["aa","cc","gg"]
     *      "30" -> ["bb"]
     *      "35" -> ["ee","ff"]
     *      "45" -> ["dd"]
     * </pre>
     *
     * @see <a href="https://stackoverflow.com/questions/39363997/converting-mapk-v-to-mapv-listk">Converting Map<K, V> to Map<V,List<K>></a>
     */
    public <V, K> Map<V, List<K>> mapInvert(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey,
                                Collectors.toList())));
    }

    /**
     * 转换结果非空函数谓语
     *
     * @param function 转换函数
     * @param <K>      转换后的类型
     * @param <T>      转换前的类型
     */
    public static <K, T> Predicate<T> notNullResultFunction(Function<T, K> function) {
        return t -> !Objects.isNull(function.apply(t));
    }

    /**
     * 转换结果为空函数谓语
     *
     * @param function 转换函数
     * @param <K>      转换后的类型
     * @param <T>      转换前的类型
     */
    public static <K, T> Predicate<T> nullResultFunction(Function<T, K> function) {
        return t -> Objects.isNull(function.apply(t));
    }

    /**
     * 根据转换结果过滤重复的元素谓语
     * <pre>
     *        list.stream().filter(distinctByFunction(l -> l.getKey())).collect(toList());
     * </pre>
     */
    public static <T> Predicate<T> distinctByFunction(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(16);
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
