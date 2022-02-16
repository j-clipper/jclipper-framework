package jclipper.common.math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static jclipper.common.math.CoordinateUtils.*;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2022/2/16 18:01.
 */
public class CoordinateUtilsTest {
    String separator = "  ";

    private int[][] COORD;

    @BeforeEach
    public void before() {
        COORD = createCoord(20, 0);
    }

    @AfterEach
    public void after() {
        printCoord(COORD, separator);
    }

    @Test
    @DisplayName("在二维数组的指定位置填充一个子二维数组")
    public void testFillCoord() {
        int[][] toFill = createCoord(5, 1);
        fillCoord(COORD, toFill, 4, 6, true);
    }

    @Test
    @DisplayName("在二维数组的指定位置用指定值填充一个指定半径的圆")
    public void test() {
        COORD = createCoord(100, 1);
         drawCircle(COORD, 50, 50, 10, 9, true);
    }


}