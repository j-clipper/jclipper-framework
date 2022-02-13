package jclipper.springboot.alert.core.impl;

import jclipper.common.utils.CollectionUtils;
import jclipper.common.utils.HttpUtils;
import jclipper.common.utils.MapBuilder;
import jclipper.springboot.alert.base.MessageConverter;
import jclipper.springboot.alert.base.NoticeConfig;
import jclipper.springboot.alert.base.NoticeMessage;
import jclipper.springboot.alert.core.AbstractAlertNotice;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 钉钉markdown 提醒通知
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/25 23:00.
 */
@Getter
@Setter
@Slf4j
public class DingTalkMarkdownAlertNotice extends AbstractAlertNotice {

    public static final String URL = "https://oapi.dingtalk.com/robot/send?access_token=";

    public DingTalkMarkdownAlertNotice(NoticeConfig config, MessageConverter messageConverter) {
        super(config, messageConverter);
    }


    @Override
    public void send(NoticeMessage message, String token) {
        if (token == null) {
            return;
        }
        String api = config.getUrl() + token;

        Map<String, Object> data = new HashMap<>(2);
        data.put("msgtype", "markdown");
        Map<String, String> md = new HashMap<>(2);
        md.put("title", messageConverter.convertTitle(message));

        Set<String> mobiles = new HashSet<>(config.getAtMobiles());
        String context = messageConverter.convertContent(message);

        MapBuilder<Object, Object> params = MapBuilder
                .of("isAtAll", config.isAtAll());
        if (CollectionUtils.isNotEmpty(mobiles)) {
            params.add("atMobiles", mobiles);
            //在text内容里要有@人的手机号，只有在群内的成员才可被@，非群内成员手机号会被脱敏。
            String atText = String.join(" ", mobiles.stream().map(s -> "@" + s).toArray(String[]::new));
            context = atText + "\n" + context;
        }
        data.put("at", params.build());

        md.put("text", context);
        data.put("markdown", md);
        HttpResponse resp = HttpRequest.post(api)
                .bodyText(HttpUtils.GSON.toJson(data), "application/json; charset=utf-8").send();
        if (log.isDebugEnabled()) {
            log.debug("response:{}", resp.bodyText());
        }
    }
}
