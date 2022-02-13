package jclipper.springboot.redis;


import jclipper.springboot.redis.lettuce.LettuceClusterClient;
import jclipper.springboot.redis.utils.CacheLettuceUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(JclipperRedisProperties.class)
@ConditionalOnProperty(prefix = JclipperRedisProperties.PREFIX, name = "enabled",havingValue = "true",matchIfMissing = true)
public class LettuceRedisAutoConfiguration {

    @Resource
    private JclipperRedisProperties jclipperRedisProperties;

    @Bean
    public LettuceClusterClient lettuceClusterClient(RedisTemplate<String, Object> redisTemplate) {
        LettuceClusterClient lettuceClusterClient = new LettuceClusterClient(redisTemplate);
        CacheLettuceUtils.initCacheClient(lettuceClusterClient);
        return lettuceClusterClient;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializerType serializerType = jclipperRedisProperties.getValueSerializer();
        RedisSerializer serializer = serializerType != null ? serializerType.getSerializer() : RedisSerializerType.JACKSON.getSerializer();
        return buildRedisTemplate(redisConnectionFactory, serializer);
    }

    public static RedisTemplate<String, Object> buildRedisTemplate(RedisConnectionFactory factory, RedisSerializer valueSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
