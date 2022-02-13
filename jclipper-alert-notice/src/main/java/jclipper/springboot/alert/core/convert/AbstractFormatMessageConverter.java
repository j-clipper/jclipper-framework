package jclipper.springboot.alert.core.convert;

import jclipper.springboot.alert.base.NoticeMessage;
import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/8 15:28.
 */
public abstract class AbstractFormatMessageConverter {

    public static final String UNKNOWN_APP_NAME = "UNKNOWN";

    public static final String DEFAULT_ALERT_TITLE_FORMAT = "【%s告警】";

    public static final Map<String, String> DEFAULT_FORMAT = new HashMap<>();


    /**
     * 合并凭借env和appName熟悉
     *
     * @param message
     * @return
     */
    protected String mergeEnvAppName(NoticeMessage message) {
        String appName = message.getAppName();
        if (Strings.isNullOrEmpty(appName)) {
            appName = UNKNOWN_APP_NAME;
        }
        if (Strings.isNullOrEmpty(message.getEnv())) {
            return appName;
        }
        return String.format("%s-%s", message.getEnv(), message.getAppName());
    }

    /**
     * 转换内容
     *
     * @param format  消息内容格式
     * @param message 消息
     * @return
     */
    protected String convertContent(String format, NoticeMessage message) {
        return String.format(format, mergeEnvAppName(message), message.getTime(), message.getHost(), message.getClient(), message.getUrl(), message.getTraceId(), message.getCode(), message.getMessage(), message.getError());
    }

    /**
     * 转换标题
     *
     * @param format  标题内容格式
     * @param message 消息
     * @return
     */
    protected String convertTitle(String format, NoticeMessage message) {
        return String.format(format, mergeEnvAppName(message));
    }
}
