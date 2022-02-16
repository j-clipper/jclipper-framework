package jclipper.common.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/20 15:07.
 */
@Getter
@AllArgsConstructor
public enum HeaderEnum implements BaseEnum<String, String> {
    /**
     *
     */
    AUTHORIZATION("Authorization", "authorization"),
    ACCEPT_LANGUAGE("Accept-Language", "语言标识"),
    X_CLIENT_IP("X-Client-Ip", "客户端IP"),
    X_USER_ID("X-User-Id", "用户ID"),
    X_SOURCE_URI("X-Source-Uri", "客户端源URI"),
    DEVICE_TYPE("device-type", "设备类型"),
    DEVICE_TOKEN("device-id", "设备token");

    /**
     * * 编码
     **/
    @ApiModelProperty(value = "编码")
    private final String code;

    /**
     * * 名称
     **/
    @ApiModelProperty(value = "名称")
    private final String name;
}
