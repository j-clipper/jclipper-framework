package jclipper.springboot.redis.lettuce;

import jclipper.springboot.redis.utils.CacheLettuceUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/12 14:15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LettuceCacheUtilTest {


    User user = User.builder()
            .age(10)
            .id(1L)
            .sex(false)
            .name("test").build();
    String key = "user:1";

    @Test
    public void testSetObject() {
        CacheLettuceUtils.delete(key);
        CacheLettuceUtils.set(key, user);

        User cache = CacheLettuceUtils.get(key);
        Assert.assertEquals(user, cache);
    }
}
