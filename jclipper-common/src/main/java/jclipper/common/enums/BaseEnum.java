package jclipper.common.enums;

import jclipper.common.utils.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/8/11 10:24.
 */
public interface BaseEnum<C, N> extends CodeEnum<C> {
    /**
     * 编码
     *
     * @return code
     */
    @Override
    C getCode();

    /**
     * 名称
     *
     * @return name
     */
    N getName();

    /**
     * 根据name查找具体的枚举值
     *
     * @param enums 枚举值数组
     * @param name  name
     * @return 匹配的枚举对象
     */
    static <E extends Enum<E> & BaseEnum<?, N>, N> E getByName(E[] enums, N name) {
        Assert.notNull(enums, "enum array not null");
        for (E e : enums) {
            if (e != null && Objects.equals(e.getName(), name)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据name查找具体的枚举值
     *
     * @param clazz 枚举类型
     * @param name  name
     * @return 匹配的枚举对象
     */
    static <E extends Enum<E> & BaseEnum<?, N>, N> E getByName(Class<E> clazz, N name) {
        Assert.notNull(clazz, "enum class not null");
        return getByName(clazz.getEnumConstants(), name);
    }

    /**
     * 根据name查找具体的枚举值
     *
     * @param enums 枚举值数组
     * @param name  name
     * @return 匹配的枚举对象
     */
    static <E extends Enum<E> & BaseEnum<?, N>, N> List<E> getListByName(E[] enums, N name) {
        Assert.notNull(enums, "enum array not null");
        return Arrays.stream(enums)
                .filter(e -> e != null && Objects.equals(e.getName(), name))
                .collect(Collectors.toList());

    }

    /**
     * 根据name查找具体的枚举值
     *
     * @param clazz 枚举类型
     * @param name  name
     * @return 匹配的枚举对象
     */
    static <E extends Enum<E> & BaseEnum<?, N>, N> List<E> getListByName(Class<E> clazz, N name) {
        Assert.notNull(clazz, "enum class not null");
        return getListByName(clazz.getEnumConstants(), name);
    }

    /**
     * 将枚举对象数组转为Map
     *
     * @param enums 枚举对象数组
     * @return Map
     */
    static <E extends Enum<E> & BaseEnum<K, V>, K, V> Map<K, V> toMap(E[] enums) {
        Assert.notNull(enums, "enum array not null");
        Map<K, V> map = new HashMap<>(enums.length);
        for (E e : enums) {
            if (e != null) {
                map.put(e.getCode(), e.getName());
            }
        }
        return map;
    }

    /**
     * 将枚举类转为Map
     *
     * @param clazz 枚举类型
     * @return Map
     */
    static <E extends Enum<E> & BaseEnum<K, V>, K, V> Map<K, V> toMap(Class<E> clazz) {
        Assert.notNull(clazz, "enum class not null");
        return toMap(clazz.getEnumConstants());
    }

    /**
     * ToString
     *
     * @param clazz 枚举类型
     * @return 格式：code1:name1;code2:name2;....
     */
    static <E extends Enum<E> & BaseEnum<?, ?>> String toString(Class<E> clazz) {
        Assert.notNull(clazz, "enum class not null");
        return Arrays.stream(clazz.getEnumConstants())
                .map(e -> e.getCode() + ":" + e.getName() + ";")
                .collect(Collectors.joining());
    }

}
