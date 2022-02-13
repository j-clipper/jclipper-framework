package jclipper.springboot.alert.core;

import jclipper.springboot.alert.base.AlertNotice;
import jclipper.springboot.alert.base.MessageConverter;
import jclipper.springboot.alert.base.NoticeConfig;
import jclipper.springboot.alert.base.NoticeMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 抽象类提醒通知
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/25 23:29.
 */
@Setter
@Getter
public abstract class AbstractAlertNotice implements AlertNotice {

    protected NoticeConfig config;

    protected MessageConverter messageConverter;

    public AbstractAlertNotice() {
    }

    public AbstractAlertNotice(NoticeConfig config, MessageConverter messageConverter) {
        this.config = config;
        this.messageConverter = messageConverter;
    }

    protected static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            2, 8, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000)
            , new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    /**
     * 是否启用
     *
     * @return
     */
    @Override
    public boolean enabled() {
        return config.isEnable() && config.getToken() != null && config.getToken().length() > 0;
    }

    /**
     * 同步发送消息
     *
     * @param message
     */
    @Override
    public void send(NoticeMessage message) {
        if (!enabled()) {
            return;
        }
        send(message, config.getToken());
    }


    /**
     * 异步发送消息
     *
     * @param message 消息
     * @param token   token
     */
    @Override
    public abstract void send(NoticeMessage message, String token);

    /**
     * 异步发送消息
     *
     * @param message
     */
    @Override
    public void sendAsync(NoticeMessage message) {
        EXECUTOR.submit(() -> send(message, config.getToken()));
    }
}
