package jclipper.springboot.alert.base;

/**
 * 告警通知器
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/6 16:50.
 */
public interface AlertNotice {
    /**
     * 是否启用
     *
     * @return true/false
     */
    boolean enabled();

    /**
     * 同步发送消息
     *
     * @param message 消息
     */
    void send(NoticeMessage message);

    /**
     * 异步发送消息
     *
     * @param message 消息
     */
    void sendAsync(NoticeMessage message);

    /**
     * 异步发送消息
     *
     * @param message 消息
     * @param token   token
     */
    void send(NoticeMessage message, String token);
}
