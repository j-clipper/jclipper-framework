package jclipper.springboot.alert.core.impl.error;

import jclipper.springboot.alert.base.MessageConverter;
import jclipper.springboot.alert.base.NoticeConfig;
import jclipper.springboot.alert.base.NoticeMessage;
import jclipper.springboot.alert.core.AbstractAlertNotice;
import jclipper.springboot.alert.core.convert.DingTalkMarkdownErrorMessageConverter;
import jclipper.springboot.alert.core.impl.DingTalkMarkdownAlertNotice;
import lombok.Getter;
import lombok.Setter;

/**
 * 钉钉markdown 错误通知告警
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/25 23:00.
 */
@Getter
@Setter
public class DingTalkMarkdownErrorAlertNotice extends AbstractAlertNotice {

    private MessageConverter messageConverter;

    private final DingTalkMarkdownAlertNotice delegate;


    public DingTalkMarkdownErrorAlertNotice(NoticeConfig config) {
        super.config = config;
        delegate = new DingTalkMarkdownAlertNotice(config, new DingTalkMarkdownErrorMessageConverter());
    }

    @Override
    public void send(NoticeMessage message, String token) {
        delegate.send(message, token);
    }
}
