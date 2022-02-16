package jclipper.common.math;


import java.util.Arrays;
import java.util.List;

/**
 * 二维数组工具类
 *
 * @author wf2311
 * @time 2016 /08/11 08:02.
 */
public class CoordinateUtils {
    /**
     * 创建一个指定默认值的二维数组
     *
     * @param x        长
     * @param y        宽
     * @param defValue 默认值
     * @return the int [ ] [ ]
     */
    public static int[][] createCoord(int x, int y, int defValue) {
        int[][] coord = new int[x][y];
        for (int i = 0; i < x; i++) {
            Arrays.fill(coord[i], defValue);
        }
        return coord;
    }

    /**
     * 创建一个指定默认值的二维数组
     *
     * @param size     the size
     * @param defValue the def value
     * @return the int [ ] [ ]
     */
    public static int[][] createCoord(int size, int defValue) {
        return createCoord(size, size, defValue);
    }


    /**
     * 在二维数组的指定位置填充一个子二维数组
     *
     * @param source           原二维数组
     * @param toFill           要填充的子二维数组
     * @param startX           开始填充的x坐标
     * @param startY           开始填充的y坐标
     * @param ignoreIfOutBound 如果子二维数组填充的目标位置超出了原二维数组的范围，是否忽略超出的部分，不忽略将抛出ArrayIndexOutOfBoundsException异常
     * @return int [][]
     * @throws ArrayIndexOutOfBoundsException
     */
    public static int[][] fillCoord(int[][] source, int[][] toFill, int startX, int startY, boolean ignoreIfOutBound) {
        int sX = source.length;
        int sY = source[0].length;
        int tX = toFill.length;
        int tY = toFill[0].length;
        int endX = startX + tX;
        int endY = startY + tY;
        if (sX < endX || sY < endY) {
            if (!ignoreIfOutBound) {
                throw new ArrayIndexOutOfBoundsException();
            }
            endX = Math.min(sX, endX);
            endY = Math.min(sY, endY);
        }
        for (int i = startX, x = 0; i < endX; i++, x++) {
            for (int j = startY, y = 0; j < endY; j++, y++) {
                source[i][j] = toFill[x][y];
            }
        }
        return source;
    }

    /**
     * 在二维数组的指定位置用指定值填充一个指定半径的圆
     *
     * @param source  原二维数组
     * @param pointX  指定位置的x坐标
     * @param pointY  指定位置的y坐标
     * @param radius  指定半径
     * @param toFill  要填充的数字
     * @param outSide true:在填充圆外的数据;false:填充圆内的数据
     * @return int [ ] [ ]
     */
    public static int[][] drawCircle(int[][] source, int pointX, int pointY, int radius, int toFill, boolean outSide) {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                if ((i - pointX) * (i - pointX) + (j - pointY) * (j - pointY) > radius * radius && outSide) {
                    source[i][j] = toFill;
                }
            }
        }
        return source;
    }

    /**
     * 在二维数组的中心用指定值填充一个内接圆
     *
     * @param source  原二维数组
     * @param toFill  要填充的值
     * @param outSide true:在填充圆外的数据;false:填充圆内的数据
     * @return int [ ] [ ]
     */
    public static int[][] drawCircle(int[][] source, int toFill, boolean outSide) {
        int pointX = source.length / 2;
        int pointY = source[0].length / 2;
        int radius = Math.min(pointX, pointY);
        return drawCircle(source, pointX, pointY, radius, toFill, outSide);
    }

    /**
     * List to coord int [ ] [ ].
     *
     * @param list the list
     * @return the int [ ] [ ]
     */
    public static int[][] list2Coord(List<int[]> list) {
        int[][] coord = new int[list.size()][list.get(0).length];
        for (int i = 0; i < coord.length; i++) {
            System.arraycopy(list.get(i), 0, coord[i], 0, coord[0].length);
        }
        return coord;
    }


    /**
     * Print coord.
     *
     * @param coord the coord
     */
    public static void printCoord(int[][] coord) {
        printCoord(coord, "\t");
    }

    /**
     * Print coord.
     *
     * @param coord the coord
     */
    public static void printCoord(int[][] coord, String separator) {
        for (int i = 0; i < coord.length; i++) {
            for (int j = 0; j < coord[0].length; j++) {
                System.out.print(coord[i][j] + separator);
            }
            System.out.println();
        }
    }

    /**
     * Coodr to array int [ ].
     *
     * @param coord the coord
     * @return the int [ ]
     */
    public static int[] coodr2Array(int[][] coord) {
        int[] arr = new int[coord.length * coord[0].length];
        for (int i = 0; i < coord.length; i++) {
            System.arraycopy(coord[i], 0, arr, i * coord.length, coord[0].length);
        }
        return arr;
    }
}
