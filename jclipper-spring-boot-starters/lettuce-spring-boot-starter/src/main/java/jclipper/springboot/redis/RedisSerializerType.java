package jclipper.springboot.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/9/28 15:36.
 */
@AllArgsConstructor
@Getter
public enum RedisSerializerType {


    /**
     *
     */
    JACKSON(JclipperRedisProperties.GENERIC_JACKSON_2_JSON_REDIS_SERIALIZER),
    JDK(new JdkSerializationRedisSerializer()),
    FASTJSON(new FastJsonRedisSerializer()),

    ;


    private RedisSerializer serializer;
}
