package jclipper.common.base;

import jclipper.common.enums.BaseErrorCode;
import jclipper.common.enums.CommonErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/20 09:09.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@ApiModel("统一响应类")
public class R<T> implements Serializable {
    /**
     * 业务响应是否成功
     */
    @ApiModelProperty(value = "业务响应是否成功")
    private Boolean success;
    /**
     * 业务响应状态码
     */
    @ApiModelProperty(value = "业务响应状态码")
    private Integer code = 200;
    /**
     * 业务响应信息
     */
    @ApiModelProperty(value = "业务响应信息")
    private String message;
    /**
     * 业务响应数据
     */
    @ApiModelProperty(value = "业务响应数据")
    private T data;

    public static <T> R<T> ok() {
        return of(200, true, null, null);
    }

    public static <T> R<T> ok(T data) {
        return of(200, true, null, data);
    }

    public static <T> R<T> ok(String message) {
        return of(200, true, message, null);
    }

    public static <T> R<T> error() {
        return error(CommonErrorCode.REQUEST_PARAM_ERROR.getCode(), null);
    }

    public static <T> R<T> paramError() {
        return error(CommonErrorCode.REQUEST_PARAM_ERROR.getCode(), CommonErrorCode.REQUEST_PARAM_ERROR.getName());
    }

    public static <T> R<T> error(String message) {
        return of(CommonErrorCode.REQUEST_PARAM_ERROR.getCode(), false, message, null);
    }

    public static <T> R<T> error(int code, String message) {
        return of(code, false, message, null);
    }

    public static <T> R<T> error(BaseErrorCode error) {
        return of(error.getCode(), false, error.getName(), null);
    }

    public static <T> R<T> error(int code) {
        return of(code, false, null, null);
    }

    public static <T> R<T> of(Integer code, Boolean success, String message, T data) {

        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMessage(message);
        r.setSuccess(success);
        return r;
    }

    public R success(boolean success) {
        this.success = success;
        return this;
    }

    public R code(int code) {
        this.code = code;
        return this;
    }

    public R message(String message) {
        this.message = message;
        return this;
    }

    public R data(T data) {
        this.data = data;
        return this;
    }
}
