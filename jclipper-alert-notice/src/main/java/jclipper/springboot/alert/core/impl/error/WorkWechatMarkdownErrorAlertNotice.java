package jclipper.springboot.alert.core.impl.error;

import jclipper.springboot.alert.base.MessageConverter;
import jclipper.springboot.alert.base.NoticeConfig;
import jclipper.springboot.alert.base.NoticeMessage;
import jclipper.springboot.alert.core.AbstractAlertNotice;
import jclipper.springboot.alert.core.convert.WorkWechatMarkdownErrorMessageConverter;
import jclipper.springboot.alert.core.impl.WorkWechatMarkdownAlertNotice;
import lombok.Getter;
import lombok.Setter;

/**
 * 企业微信markdown 错误消息告警
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/25 23:00.
 */
@Getter
@Setter
public class WorkWechatMarkdownErrorAlertNotice extends AbstractAlertNotice {

    private MessageConverter messageConverter;

    private final WorkWechatMarkdownAlertNotice delegate;


    public WorkWechatMarkdownErrorAlertNotice(NoticeConfig config) {
        super.config = config;
        delegate = new WorkWechatMarkdownAlertNotice(config, new WorkWechatMarkdownErrorMessageConverter());
    }

    @Override
    public void send(NoticeMessage message, String token) {
        delegate.send(message, token);
    }
}
