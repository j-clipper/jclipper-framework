package jclipper.common.utils;

import com.mortennobel.imagescaling.ResampleOp;
import jclipper.common.enums.ColorCode;
import jclipper.common.enums.ImageType;
import jclipper.common.math.CoordinateUtils;
import jclipper.common.time.DateHelper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 头像合并工具类
 *
 * @author wf2311
 * @since 2016 /08/15 17:00.
 */
@Slf4j
public class MergeImage<S> {

    private static final int DEFAULT_COLOR = ColorCode.White.getCode();

    private int padding = 5;

    private final int width;
    private final boolean drawCircle;
    private final List<S> images;
    private final Function<S, BufferedImage> imageFunction;

    private BufferedImage output;

    public MergeImage(int width, boolean drawCircle, List<S> images, Function<S, BufferedImage> imageFunction) {
        this.width = width;
        this.drawCircle = drawCircle;
        this.images = images;
        this.imageFunction = imageFunction;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }


    private int calculateStartLocation(int totalWidth, int width, int num, int scale, int padding) {
        if (num == scale) {
            return padding;
        }
        return (totalWidth - num * width - (num - 1) * padding) / 2;
    }

    public synchronized BufferedImage merge() {
        if (output != null) {
            return output;
        }
        return doMerge(calculate());
    }

    @SneakyThrows
    public File outputToPath(String path, String name, ImageType type, int imageSize) {
        if (path == null) {
            path = ".";
        }
        if (name == null) {
            name = DateHelper.timestamp() + "";
        }
        if (type == null) {
            type = ImageType.PNG;
        }

        File outFile = new File(path + File.separator + name + "." + type.getCode());
        BufferedImage image = MergeImage.resize(merge(), imageSize, imageSize);

        ImageIO.write(image, "jpg", outFile);
        return outFile;
    }

    private Elements calculate() {

        Elements elements = new Elements();
        elements.setDrawCircle(drawCircle);
        int scale = calculateScale(images.size());

        int size = Math.min(scale * scale, images.size());
        int row = size / scale + (size % scale == 0 ? 0 : 1);
        int totalPadding = (scale + 1) * padding;
        int childWidth = (width - totalPadding) / scale;

        int startX = calculateStartLocation(width, childWidth, row, scale, padding);
        for (int i = 0; i < row; i++) {
            int rowSize = Math.min(size - i * scale, scale);
            int rowStartY = calculateStartLocation(width, childWidth, rowSize, scale, padding);
            for (int j = 0; j < rowSize; j++) {
                Element e = new Element();
                e.setStartX(startX);
                e.setStartY(rowStartY);
                S image = images.get(i * scale + j);
                e.setRgbs(getImageRgbs(imageFunction.apply(image), childWidth, childWidth));
                elements.add(e);
                rowStartY = rowStartY + childWidth + padding;
            }
            startX = startX + childWidth + padding;
        }

        return elements;
    }

    private BufferedImage doMerge(Elements elements) {
        int[][] full = CoordinateUtils.createCoord(width, DEFAULT_COLOR);
        for (Element element : elements) {
            int[][] rgbs = element.getRgbs();
            if (elements.drawCircle) {
                CoordinateUtils.drawCircle(rgbs, DEFAULT_COLOR, true);
            }
            CoordinateUtils.fillCoord(full, rgbs, element.getStartX(), element.getStartY(), true);
        }
        int[] array = CoordinateUtils.coodr2Array(full);

        output = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
        output.setRGB(0, 0, width, width, array, 0, width);
        return output;

    }


    @Data
    private static class Elements extends ArrayList<Element> {
        private int padding;
        private int elementWidth;
        private boolean drawCircle;
    }

    @Data
    private static class Element {
        private int startX;
        private int startY;
        private int[][] rgbs;

    }


    private static int calculateScale(int num) {
        if (num < 1) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        if (num <= 4) {
            return 2;
        }
        if (num <= 9) {
            return 3;
        }
        if (num <= 16) {
            return 4;
        }
        if (num <= 25) {
            return 5;
        }
        return 6;
    }


    /**
     * 从图像URL中获取RGB数据保存在二维数组中
     *
     * @param source the source  BufferedImage
     * @param width  the width
     * @param height the height
     * @return int [ ] [ ]
     */
    public static int[][] getImageRgbs(BufferedImage source, int width, int height) {
        try {
            BufferedImage bufferedImage = resize(source, width, height);
            int[][] imageRgbs = new int[width][height];
            for (int i = 0; i < height; i++) {
                int[] imageRgb = new int[width];
                imageRgb = bufferedImage.getRGB(0, i, width, 1, imageRgb, 0, width);
                System.arraycopy(imageRgb, 0, imageRgbs[i], 0, width);
            }
            return imageRgbs;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 重新设置图片的宽度高度
     *
     * @param inputBufImage the input buf image
     * @param width         新的宽度<br>
     * @param height        新的高度<br>
     * @return 返回BufferedImage图像流 buffered image
     */
    public static BufferedImage resize(BufferedImage inputBufImage, int width, int height) {
        try {
            ResampleOp resampleOp = new ResampleOp(width, height);
            return resampleOp.filter(inputBufImage, null);
        } catch (Exception e) {
        }

        return null;
    }


    /**
     * Convert buffered image.
     *
     * @param imgUrl the img url
     * @return the buffered image
     */
    public static BufferedImage convert(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            return ImageIO.read(url);
        } catch (IOException e) {
        }
        return null;
    }

}
