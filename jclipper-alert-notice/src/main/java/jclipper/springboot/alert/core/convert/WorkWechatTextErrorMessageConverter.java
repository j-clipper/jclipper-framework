package jclipper.springboot.alert.core.convert;

import jclipper.springboot.alert.base.MessageConverter;
import jclipper.springboot.alert.base.NoticeMessage;

/**
 * 企业微信文本 错误消息转换器
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/6 16:40.
 */
public class WorkWechatTextErrorMessageConverter extends AbstractFormatMessageConverter implements MessageConverter {


    private static final String CONTENT_FORMAT = "【%s告警】\n" +
            "时间: %s\n" +
            "服务端: %s\n" +
            "客户端: %s\n" +
            "访问链接: %s\n" +
            "追踪码: %s\n" +
            "响应编码: %s\n" +
            "消息: %s\n" +
            "异常信息: %s";

    static {
        DEFAULT_FORMAT.put(WorkWechatTextErrorMessageConverter.class.getName(), WorkWechatTextErrorMessageConverter.CONTENT_FORMAT);
    }

    @Override
    public String convertContent(NoticeMessage message) {
        return convertContent(DEFAULT_FORMAT.getOrDefault(this.getClass().getName(), CONTENT_FORMAT), message);
    }

    @Override
    public String convertTitle(NoticeMessage message) {
        return convertTitle(DEFAULT_ALERT_TITLE_FORMAT, message);
    }
}
