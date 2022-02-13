package jclipper.springboot.alert.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 告警通知配置类
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/6 17:11.
 */
@Data
public class NoticeConfig implements Serializable {
    /**
     * token
     */
    private String token;
    /**
     * 是否启用，默认为false
     */
    private boolean enable = false;
    /**
     * 需要艾特的手机号码（非必选）
     */
    private Set<String> atMobiles;
    /**
     * 是否需要艾特所有人，默认为false
     */
    private boolean atAll = false;

    /**
     * url
     */
    private String url;

}
