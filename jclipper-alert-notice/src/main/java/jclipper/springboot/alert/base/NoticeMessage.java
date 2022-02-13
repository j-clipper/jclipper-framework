package jclipper.springboot.alert.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警通知内容，可以扩展
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/25 23:22.
 */
@Data
public class NoticeMessage {
    public static final int TEXT_LIMIT = 512;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 时间
     */
    private LocalDateTime time;
    /**
     * 服务端
     */
    private String host;
    /**
     * 客户端
     */
    private String client;
    /**
     * 访问的url
     */
    private String url;
    /**
     * TraceId
     */
    private String traceId;
    /**
     * 响应状态码
     */
    private String code;
    /**
     * 提示的信息
     */
    private String message;
    /**
     * 错误信息，一般为exception.getMessage
     */
    private String error;

    /**
     * 环境标志，非必须
     */
    private String env;

    public void setError(String error) {
        this.error = limitTextLength(error);
    }

    public void setMessage(String message) {
        this.message = limitTextLength(message);
    }

    private String limitTextLength(String text) {
        if (text == null || text.length() < TEXT_LIMIT) {
            return text;
        }
        return text.substring(0, TEXT_LIMIT);

    }
}
