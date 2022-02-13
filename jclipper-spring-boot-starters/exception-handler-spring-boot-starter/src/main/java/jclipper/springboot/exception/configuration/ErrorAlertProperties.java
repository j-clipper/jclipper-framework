package jclipper.springboot.exception.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/12/6 15:26.
 */
@ConfigurationProperties(ErrorAlertProperties.PREFIX)
@Data
public class ErrorAlertProperties {
    public static final String PREFIX = "jclipper.alert";

    /**
     * 业务或参数错误是否需要告警，默认为false
     */
    private boolean alertWhenBizCode = false;

    /**
     * 环境名称，例如：test-20.2,pre-207，非必填，当填写该值时，会在告警信息标题中显示环境名称
     */
    private String env;

    /**
     * 进行告警时需要艾特用户的手机号码，非必填
     */
    private Set<String> globalAtMobiles = new HashSet<>();
}
