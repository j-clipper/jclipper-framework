package jclipper.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 几种常见的图片格式
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2022/2/16 23:55.
 */
@Getter
@AllArgsConstructor
public enum ImageType implements BaseEnum<String, String> {
    /**
     * 几种常见的图片格式
     */
    GIF("gif", "图形交换格式"),
    JPG("jpg", "联合照片专家组"),
    JPEG("jpeg", "联合照片专家组"),
    PNG("png", "可移植网络图形"),
    BMP("bmp", "英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式"),
    ;

    private final String code;

    private final String name;

}
