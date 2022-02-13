package jclipper.springboot.redis.lettuce;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通用缓存客户端接口
 */
public interface CacheLettuceClient {

    void flushAll();

    Long ttl(String key);

    Set<String> keys(String pattern);

    Long expire(String key, int seconds);

    Boolean exists(String key);

    <T> Boolean set(String key, T value);

    <T> Boolean set(String key, int seconds, T value);

    Boolean set(String key, String value, String nxxx, String expx, long time);

    Boolean eval(String script, List<String> keys, List<String> args);

    List<Object> batchSet(List<String> keys, List<Object> values);

    <T> T get(String key);

    byte[] getBytes(String key);

    <T> List<T> batchGet(String[] keys);

    <T> T get(String key, Class<T> t);

    Boolean delete(String key);

    void batchDelete(String[] keys);

    <T> Long lpush(String key, T value);

    <T> Long lpushAll(String key, T... values);

    <T> Long lpushAll(String key, Collection<T> values);

    <T> Long rpush(String key, T value);

    <T> Long rpushAll(String key, T... values);

    <T> Long rpushAll(String key, Collection<T> values);

    <T> List<T> lrange(String key, int start, int end);

    Long llen(String key);

    <T> T rpop(String key);

    <T> T lpop(String key);

    Long decrBy(String key, long integer);

    Long incr(String key);

    Long incrBy(String key, long integer);

    Boolean hset(String key, String field, Object value);

    <T> Boolean hmset(String key, Map<String, T> map);

    Object hget(String key, String field);

    byte[] hgetBytes(String key, String field);

    Long hincrBy(String key, String field, long integer);

    List<Object> hmget(String key, String... fields);

    List<String> hmget_str(String key, String... fields);

    Boolean hexists(String key, String field);

    Set<String> hkeys(String key);

    Map<String, Object> hgetAll(String key);

    Long hdel(String key, String field);

    Boolean publish(String channel, String message);

    <T> Long sadd(String key, List<T> values);

    Long sadd(String key, Object... values);

    Long sadd(String key, Object value);

    <T> Set<T> smembers(String key);

    Boolean sismember(String key, Object value);

    Long srem(String key, Object... values);

    <T> Long srem(String key, List<T> values);

    Long srem(String key, Object value);

    Long scard(String key);

    Boolean zadd(String key, double score, Object member);

    Long zadd(String key, Map<Object, Double> scoreMembers);

    Double zincrby(String key, double increment, Object member);

    Long zrem(String key, Object value);

    <T> List<T> zrange(String key, long start, long end, boolean reverse);

    <T> Map<T, Double> zrangeWithScores(String key, long start, long end);

    <T> List<T> zrangeByScore(String key, double min, double max);

    <T> Map<T, Double> zrangeByScoreWithScores(String key, double min, double max);

    Long zcard(String key);

    Double zscore(String key, Object member);

    Long zcount(String key, double min, double max);

    Long zrank(String key, Object member, boolean reverse);

    Long expireAt(String key, long unixTime);
}
