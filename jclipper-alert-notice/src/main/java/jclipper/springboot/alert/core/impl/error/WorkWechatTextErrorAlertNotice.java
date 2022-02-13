package jclipper.springboot.alert.core.impl.error;

import jclipper.springboot.alert.base.NoticeConfig;
import jclipper.springboot.alert.base.NoticeMessage;
import jclipper.springboot.alert.core.AbstractAlertNotice;
import jclipper.springboot.alert.core.convert.WorkWechatTextErrorMessageConverter;
import jclipper.springboot.alert.core.impl.WorkWechatTextAlertNotice;
import lombok.Getter;
import lombok.Setter;

/**
 * 企业微信文本 错误消息告警
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/25 23:00.
 */
@Getter
@Setter
public class WorkWechatTextErrorAlertNotice extends AbstractAlertNotice {

    private final WorkWechatTextAlertNotice delegate;


    public WorkWechatTextErrorAlertNotice(NoticeConfig config) {
        super.config = config;
        delegate = new WorkWechatTextAlertNotice(config, new WorkWechatTextErrorMessageConverter());
    }

    @Override
    public void send(NoticeMessage message, String token) {
        delegate.send(message, token);
    }
}
