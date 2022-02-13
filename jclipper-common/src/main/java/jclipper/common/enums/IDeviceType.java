package jclipper.common.enums;

/**
 * 设备类型
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/9/9 11:00.
 */
public interface IDeviceType extends BaseCode<Integer, String> {

    String DEVICE = "device";

    String DEVICE_MOBILE = "m";

    String DEVICE_MOBILE_IOS = "ios";

    String DEVICE_MOBILE_ANDROID = "android";

    String ASYN = "asyn";

    /**
     * 数字代号
     *
     * @return
     */
    @Override
    Integer getCode();

    /**
     * 值
     *
     * @return
     */
    String getValue();

    /**
     * 名称
     * @return
     */
    @Override
    default String getName() {
        return getValue();
    }

    /**
     * 描述
     *
     * @return
     */
    String getDesc();
}
