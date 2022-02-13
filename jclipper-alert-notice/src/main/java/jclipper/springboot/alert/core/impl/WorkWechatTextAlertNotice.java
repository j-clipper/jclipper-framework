package jclipper.springboot.alert.core.impl;

import jclipper.common.utils.CollectionUtils;
import jclipper.common.utils.HttpUtils;
import jclipper.springboot.alert.base.MessageConverter;
import jclipper.springboot.alert.base.NoticeConfig;
import jclipper.springboot.alert.base.NoticeMessage;
import jclipper.springboot.alert.core.AbstractAlertNotice;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 企业微信文本 提醒通知
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/6 10:54.
 */
@Getter
@Setter
@Slf4j
public class WorkWechatTextAlertNotice extends AbstractAlertNotice {

    public WorkWechatTextAlertNotice(NoticeConfig config, MessageConverter messageConverter) {
        super(config, messageConverter);
    }

    @Override
    public void send(NoticeMessage message, String token) {
        if (token == null) {
            return;
        }
        String api = config.getUrl() + token;

        Map<String, Object> data = new HashMap<>(2);
        String contextType = "text";

        Map<String, Object> sub = new HashMap<>(2);

        boolean atAll = this.config.isAtAll();
        List<String> ats = new ArrayList<>();
        Set<String> mobiles = this.config.getAtMobiles();
        if (CollectionUtils.isNotEmpty(mobiles) || atAll) {
            if (mobiles != null) {
                ats.addAll(mobiles);
            }
            if (atAll) {
                ats.add("@all");
            }
            sub.put("mentioned_mobile_list", ats);
        }

        data.put("msgtype", contextType);

        sub.put("content", messageConverter.convertContent(message));
        data.put(contextType, sub);
        HttpResponse resp = HttpRequest.post(api)
                .bodyText(HttpUtils.GSON.toJson(data), "application/json; charset=utf-8").send();
        if (log.isDebugEnabled()) {
            log.info("response:{}", resp.bodyText());
        }
    }
}
