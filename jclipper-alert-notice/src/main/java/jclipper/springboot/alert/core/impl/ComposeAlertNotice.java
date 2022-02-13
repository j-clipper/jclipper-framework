package jclipper.springboot.alert.core.impl;

import jclipper.springboot.alert.base.AlertNotice;
import jclipper.springboot.alert.base.NoticeMessage;

import java.util.Collections;
import java.util.List;

/**
 * 组合通知警告
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/6 11:28.
 */
public class ComposeAlertNotice implements AlertNotice {
    private final List<AlertNotice> alerts;

    public ComposeAlertNotice(List<AlertNotice> alerts) {
        if (alerts != null) {
            this.alerts = alerts;
        } else {
            this.alerts = Collections.emptyList();
        }
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public void send(NoticeMessage message) {
        for (AlertNotice alert : alerts) {
            alert.send(message);
        }
    }

    @Override
    public void sendAsync(NoticeMessage message) {
        for (AlertNotice alert : alerts) {
            alert.sendAsync(message);
        }
    }

    @Override
    public void send(NoticeMessage message, String token) {
        for (AlertNotice alert : alerts) {
            alert.send(message, token);
        }
    }
}
