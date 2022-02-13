package jclipper.springboot.alert;

import jclipper.springboot.alert.base.AlertNotice;
import jclipper.springboot.alert.base.NoticeMessage;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/7 09:48.
 */
public class TestAlerts extends TestApplication {

    @Resource(name = "dingTalkMarkdownErrorAlert")
    private AlertNotice dingTalkMarkdownErrorAlert;

    @Resource(name = "workWechatMarkdownErrorAlert")
    private AlertNotice workWechatMarkdownErrorAlert;

    @Resource(name = "workWechatTextErrorAlert")
    private AlertNotice workWechatTextErrorAlert;


    @Resource
    private AlertNotice alertNotice;


    @Test
    public void testSendWorkWechatMarkdownError() {
        workWechatMarkdownErrorAlert.send(build("这是一条发送到微信的markdown消息"));
    }

    @Test
    public void testSendWorkWechatTextError() {
        workWechatTextErrorAlert.send(build("这是一条发送到微信的文本消息"));
    }

    @Test
    public void testSendDingTalkMarkdownError() {
        dingTalkMarkdownErrorAlert.send(build("这是一条发送到钉钉的markdown消息"));
    }


    @Test
    public void testSendAll() {
        alertNotice.send(build("这是一条组合消息，会同时发送到微信和钉钉"));
    }

    private NoticeMessage build(String msg) {
        NoticeMessage m = new NoticeMessage();
        m.setAppName("jclipper-alert-notice");
        m.setTime(LocalDateTime.now());
        m.setCode("400");
        m.setError("这是测试消息，请忽略🏷😁");
        m.setMessage(msg);
        m.setTraceId(UUID.randomUUID().toString());
        m.setUrl("/test/alerts");
        m.setHost("192.168.150.78");
        m.setClient("127.0.0.1");
        m.setMessage(msg);
        return m;

    }
}
