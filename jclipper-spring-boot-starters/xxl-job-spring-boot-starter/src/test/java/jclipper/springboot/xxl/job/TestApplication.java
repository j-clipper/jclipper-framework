package jclipper.springboot.xxl.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/21 13:33.
 */
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TestApplication.class, args);
        TimeUnit.MINUTES.sleep(10);
    }
}
