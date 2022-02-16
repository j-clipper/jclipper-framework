package jclipper.common.enums;

import jclipper.common.utils.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2022/2/17 00:01.
 */
public interface CodeEnum<C> extends Serializable {
    /**
     * 编码
     *
     * @return
     */
    C getCode();

    /**
     * 根据ID查找具体的枚举值
     *
     * @param enums 枚举值数组
     * @param code  code
     * @return 匹配的枚举对象
     */
    static <E extends Enum<E> & CodeEnum<C>, C> E getByCode(E[] enums, C code) {
        for (E e : enums) {
            if (e != null && Objects.equals(e.getCode(), code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据ID查找具体的枚举值
     *
     * @param clazz 枚举类型
     * @param code  code
     * @return 匹配的枚举对象
     */
    static <E extends Enum<E> & CodeEnum<C>, C> E getByCode(Class<E> clazz, C code) {
        return getByCode(clazz.getEnumConstants(), code);
    }

    /**
     * 将枚举对象数组转为Set
     *
     * @param enums 枚举对象数组
     * @return Set of code
     */
    static <E extends Enum<E> & CodeEnum<C>, C> Set<C> toSet(E[] enums) {
        Assert.notNull(enums, "enum array not null");
        Set<C> set = new HashSet<>(enums.length);
        for (E e : enums) {
            if (e != null) {
                set.add(e.getCode());
            }
        }
        return set;
    }

    /**
     * 将枚举类转为Map
     *
     * @param clazz 枚举对象数组
     * @return Set of code
     */
    static <E extends Enum<E> & CodeEnum<C>, C> Set<C> toSet(Class<E> clazz) {
        Assert.notNull(clazz, "enum class not null");
        return toSet(clazz.getEnumConstants());
    }


    /**
     * 将枚举对象数组转为Set
     *
     * @param enums 枚举对象数组
     * @return Set of code
     */
    static <E extends Enum<E> & CodeEnum<C>, C> List<C> toList(E[] enums) {
        Assert.notNull(enums, "enum array not null");
        List<C> list = new ArrayList<>(enums.length);
        for (E e : enums) {
            if (e != null) {
                list.add(e.getCode());
            }
        }
        return list;
    }

    /**
     * 将枚举类转为Map
     *
     * @param clazz 枚举对象数组
     * @return Set of code
     */
    static <E extends Enum<E> & CodeEnum<C>, C> List<C> toList(Class<E> clazz) {
        Assert.notNull(clazz, "enum class not null");
        return toList(clazz.getEnumConstants());
    }

}
