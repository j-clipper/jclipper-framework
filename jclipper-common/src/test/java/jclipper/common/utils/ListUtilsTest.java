package jclipper.common.utils;

import com.google.common.collect.Lists;
import jclipper.common.utils.ListUtils;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/11/16 17:20.
 */
public class ListUtilsTest extends TestCase {

    public static class E {
        private Integer id;
        private String name;

        public E(Integer id) {
            this.id = id;
            this.name = id*id+"";
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void test1() {
        ArrayList<E> list = Lists.newArrayList(new E(1), new E(2), new E(3), new E(3));
        Map<Integer, List<E>> map = ListUtils.group(list, E::getId);
        System.out.println(map.size());
    }

}