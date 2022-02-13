package jclipper.springboot.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2021/9/28 15:32.
 */
@ConfigurationProperties(prefix = JclipperRedisProperties.PREFIX)
@Data
public class JclipperRedisProperties {
    public static final String PREFIX = "jclipper.redis";

    public static final GenericJackson2JsonRedisSerializer GENERIC_JACKSON_2_JSON_REDIS_SERIALIZER;

    static {
        ObjectMapper om = new ObjectMapper();
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        om.registerModule((new SimpleModule()));
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        GENERIC_JACKSON_2_JSON_REDIS_SERIALIZER = new GenericJackson2JsonRedisSerializer(om);

    }

    private RedisSerializerType valueSerializer = RedisSerializerType.JACKSON;

    private boolean enabled = true;
}
