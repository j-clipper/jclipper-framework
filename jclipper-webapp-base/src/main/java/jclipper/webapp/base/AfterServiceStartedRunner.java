package jclipper.webapp.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/6/5 15:37.
 */
@Slf4j
public class AfterServiceStartedRunner implements ApplicationRunner {
    /**
     * 会在服务启动完成后立即执行
     */
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Successful Application Startup!");
    }

}
