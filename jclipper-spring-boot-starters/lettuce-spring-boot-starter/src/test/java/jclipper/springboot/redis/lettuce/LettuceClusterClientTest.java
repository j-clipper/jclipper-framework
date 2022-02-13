package jclipper.springboot.redis.lettuce;

import jclipper.springboot.redis.utils.CacheLettuceUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/23 20:08.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LettuceClusterClientTest {
    public void before() {

    }

    @Autowired
    private LettuceClusterClient client;

    @Test
    public void testSetNumber() {
        String key1 = "test:number:i:1";
        String key2 = "test:number:l:2";
        String key3 = "test:number:d:3";
        String key4 = "test:number:b:4";
        int i1 = 129;
        client.set(key1, i1);
        Assert.assertEquals(i1, (int) client.get(key1));

        long l2 = 12900000000000000L;
        client.set(key2, l2);
        Assert.assertEquals(l2, (long) client.get(key2));


        double d3 = 123456.678900011111;
        client.set(key3, d3);
        double actual = client.get(key3);
        Assert.assertEquals(d3, actual, 10);

        boolean b4 = false;
        client.set(key4, b4);
        Assert.assertEquals(b4, (boolean) client.get(key4));
    }

    User user = User.builder()
            .age(10)
            .id(1L)
            .sex(false)
            .name("test").build();
    String key = "user:1";

    @Test
    public void testSetObject() {
        client.delete(key);
        client.set(key, user);

        User cache = client.get(key);
        Assert.assertEquals(user, cache);
    }

    @Test
    public void testSetObjectList() {
        String key = "user:list:1";
        client.delete(key);
        List<User> o = Collections.singletonList(user);
        client.set(key, o);
        List<User> cache = client.get(key);
        Assert.assertEquals(cache, o);
    }


    @Test
    public void testLpush() {
        String key = "test:list:users";
        client.delete(key);
        client.lpush(key, user);
        User o = client.lpop(key);
        Assert.assertEquals(o, user);
    }

    @Test
    public void tedtLrange() {
        String key = "test:list:users2";
        client.delete(key);
        client.lpush(key, user);
        client.lpush(key, user);
        client.lpush(key, user);
        client.lpush(key, user);
        client.lpush(key, user);
        long size = client.llen(key);
        Assert.assertEquals(5L, size);
        List<User> lrange = client.lrange(key, 0, (int) size - 1);
        Assert.assertEquals(5, lrange.size());
        List<User> lrange2 = client.lrange(key, 0, (int) size);
        Assert.assertEquals(5, lrange2.size());

    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testGetBytes() {
        String key = "test:b:1";
        client.delete(key);
        client.set(key, user);
        byte[] bytes = client.getBytes(key);
        Assert.assertNotNull(bytes);
        User cache = (User) (redisTemplate.getValueSerializer()).deserialize(bytes);
        Assert.assertEquals(user, cache);
    }

    @Test
    public void testInc() {
        String key = "test:number:10";
        client.delete(key);
        long r = client.incr(key);
        Assert.assertEquals(1L, r);
        r = client.incrBy(key, 3L);
        Assert.assertEquals(4L, r);
        r = client.decrBy(key, 10L);
        Assert.assertEquals(-6, r);
        r = client.incrBy(key, -10);
        Assert.assertEquals(-16, r);
        r = client.decrBy(key, -17);
        Assert.assertEquals(1, r);
        Assert.assertEquals(r, (long) client.incrBy(key, 0));
    }


    @Test
    public void testSadd() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User u = User.builder().id(1L).name(i * i + "").build();
            users.add(u);
        }
        String key = "test:a:b:c";
//        client.sadd(key, users);
        Set<User> mems = client.smembers(key);
        System.out.println(mems);
    }

    @Test
    public void testSadd2() {
        List<Long> ids = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            ids.add(i);
        }
        String key = "test:a:b:c:d";
//        client.sadd(key, ids);
        Set<Long> mems = client.smembers(key);
        System.out.println(mems.size());
    }

    @Test
    public void test2() {

        User build = User.builder().name("1222").time(LocalDateTime.now()).build();

        CacheLettuceUtils.set("test.time", LocalDateTime.now());
        Object time = CacheLettuceUtils.get("test.time");
        System.out.println(time);
    }


}