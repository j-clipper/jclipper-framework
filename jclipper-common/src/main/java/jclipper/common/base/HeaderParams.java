package jclipper.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/20 15:09.
 */
@Data
@NoArgsConstructor
public class HeaderParams {
    /**
     * * 客户端IP
     **/
    @ApiModelProperty(value = "客户端IP")
    private String xClientIp;

    /**
     * * 客户端版本号
     **/
    @ApiModelProperty(value = "客户端版本号")
    private String xClientVersion;

    /**
     * * 设备标识
     **/
    @ApiModelProperty(value = "设备标识")
    private String xDeviceType;

    /**
     * * 设备token
     **/
    @ApiModelProperty(value = "设备token")
    private String xDeviceToken;

    /**
     * * 身份验证
     **/
    @ApiModelProperty(value = "身份验证")
    private String authorization;
}
