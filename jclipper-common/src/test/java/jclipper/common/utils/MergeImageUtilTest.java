package jclipper.common.utils;

import jclipper.common.enums.ImageType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2022/2/16 19:53.
 */
class MergeImageUtilTest {

    private static final String URL = "https://file.wf2311.com/wf2311.png";

    @SneakyThrows
    private void createMergeImage(int num, boolean drawCircle, int imagePx) {
        List<String> urls = IntStream.rangeClosed(1, num).boxed().map(i -> URL).collect(Collectors.toList());
        MergeImage<String> mergeImage = new MergeImage<>(imagePx, drawCircle, urls, MergeImage::convert);
        mergeImage.outputToPath("/Users/wf2311/Downloads/", null, ImageType.PNG, imagePx);
    }

    @Test
    void merge() {
        for (int i = 1; i < 17; i++) {
            createMergeImage(i, false, 200);
        }
    }

    @Test
    void merge2() {
        for (int i = 1; i < 36; i++) {
            createMergeImage(i, true, 300);
        }
    }
}