package jclipper.springboot.alert.base;

/**
 * 消息转换器
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/6 16:39.
 */
public interface MessageConverter {

    /**
     * 转换内容
     *
     * @param message 消息
     * @return text
     */
    String convertContent(NoticeMessage message);

    /**
     * 转换标题
     *
     * @param message 消息
     * @return text
     */
    String convertTitle(NoticeMessage message);


}
