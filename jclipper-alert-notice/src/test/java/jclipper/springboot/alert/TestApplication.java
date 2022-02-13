package jclipper.springboot.alert;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/23 20:34.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ComponentScan
//@ActiveProfiles("local")
public class TestApplication {
}
