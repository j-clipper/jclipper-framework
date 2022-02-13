package jclipper.common.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/8/11 13:45.
 */
@AllArgsConstructor
@Getter
public enum DeviceType implements IDeviceType {
    /**
     *
     */
    UNKNOWN("Unknown", 0, "未识别的"),
    WEB("Web", 1, "web"),
    ANDROID("Android", 2, "Android"),
    IOS("iOS", 3, "iOS"),
    PC_APP("Windows", 4, "Windows"),
    MOBILE("Mobile", 5, "Mobile"),
    H5("m-browser", 6, "H5页面"),
    WEIXIN("weixin", 7, "微信"),
    PAD("pad", 8, "Pad"),
    ;

    /**
     * * 值
     **/
    @ApiModelProperty(value = "值")
    private String value;
    /**
     * * 编码
     **/
    @ApiModelProperty(value = "编码")
    private Integer code;
    /**
     * * 描述
     **/
    @ApiModelProperty(value = "描述")
    private String desc;

    public static DeviceType get(String name) {
        if (name == null) {
            return WEB;
        }

        for (DeviceType deviceTypeEnum : DeviceType.values()) {
            if (name.equalsIgnoreCase(deviceTypeEnum.getValue())) {
                return deviceTypeEnum;
            }
        }

        return WEB;
    }

    public static DeviceType get(Integer type) {

        for (DeviceType deviceTypeEnum : DeviceType.values()) {
            if (deviceTypeEnum.getCode().equals(type)) {
                return deviceTypeEnum;
            }
        }
        return WEB;
    }

    public static boolean isMobileDevice(String deviceType) {
        if (deviceType == null || deviceType.trim().length() == 0) {
            return false;
        }
        return ANDROID.getValue().equalsIgnoreCase(deviceType) || IOS.getValue().equalsIgnoreCase(deviceType);
    }

    public static boolean isMobileDevice(Integer code) {
        if (code == null) {
            return false;
        }
        return ANDROID.getCode().equals(code) || IOS.getCode().equals(code) || MOBILE.getCode().equals(code);
    }
}
