package jclipper.micrometer.extend.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/10/25 11:14.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class MicrometerInternalSpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (MicrometerInternalSpringUtil.applicationContext == null) {
            MicrometerInternalSpringUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {

        ApplicationContext context = getApplicationContext();
        if (context == null) {
            return null;
        }
        return context.getBean(name);
    }

}