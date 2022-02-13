package jclipper.common.utils;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/15 09:49.
 */
public class PropertiesUtil {
    public static final String SERVER_BOOTSTRAP_TIME_KEY = "server.bootstrap.time";

    private PropertiesUtil() {
    }


    /**
     * 向系统变量中注入服务启动时间
     */
    public static void putServerBootstrapTime() {
        System.setProperty(SERVER_BOOTSTRAP_TIME_KEY, LocalDateTime.now().toString());
    }

    /**
     * 获取服务启动时间
     *
     * @return
     */
    public static LocalDateTime getServerBootstrapTime() {
        String property = System.getProperty(SERVER_BOOTSTRAP_TIME_KEY);
        if (property != null) {
            return LocalDateTime.parse(property);
        }
        putServerBootstrapTime();
        return getServerBootstrapTime();
    }
}
