package jclipper.common.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/9/29 19:23.
 */
@Data
public class CustomDeviceType implements IDeviceType, Serializable {
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
}
