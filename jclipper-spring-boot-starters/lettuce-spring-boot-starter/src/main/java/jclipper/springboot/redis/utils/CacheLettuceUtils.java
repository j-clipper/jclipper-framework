package jclipper.springboot.redis.utils;

import jclipper.common.utils.CollectionUtils;
import jclipper.springboot.redis.lettuce.CacheLettuceClient;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

/**
 * 缓存工具类
 */
public class CacheLettuceUtils {

    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    static CacheLettuceClient cacheClient = null;

    /**
     * 初始化cacheClient
     *
     * @param cacheClient
     */
    public static synchronized void initCacheClient(CacheLettuceClient cacheClient) {
        if (CacheLettuceUtils.cacheClient == null) {
            CacheLettuceUtils.cacheClient = cacheClient;
        }
    }

	/*public static void flushAll() {
		cacheClient.flushAll();
	}*/

    public static Long ttl(String key) {
        return cacheClient.ttl(key);
    }

    public static Set<String> keys(String pattern) {
        return cacheClient.keys(pattern);
    }

    public static Long expire(String key, int seconds) {
        return cacheClient.expire(key, seconds);
    }

    public static Long expireAt(String key, long unixTime) {
        return cacheClient.expireAt(key, unixTime);
    }

    public static Boolean exists(String key) {
        return cacheClient.exists(key);
    }

    public static <T extends Serializable> Boolean set(String key, T value) {
        boolean result = cacheClient.set(key, value);
        if (!result) {
            result = cacheClient.set(key, value);
        }
        return result;
    }

    public static <T extends Serializable> Boolean set(String key, int expiredTime, T value) {
        boolean result = cacheClient.set(key, expiredTime, value);
        if (!result) {
            result = cacheClient.set(key, expiredTime, value);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) cacheClient.get(key);
    }

    public static byte[] getBytes(String key) {
        return cacheClient.getBytes(key);
    }

    public static <T> List<T> batchGet(String[] keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }

        List<T> result = new ArrayList<>(keys.length);
        for (String key : keys) {
            result.add(get(key));
        }

        return result;
    }

    public static <T> List<T> batchGet(List<String> keyList) {
        if (keyList.isEmpty()) {
            return null;
        }
        String[] keysString = new String[keyList.size()];
        keyList.toArray(keysString);
        return batchGet(keysString);
    }

    public static Boolean delete(String key) {
        boolean result = cacheClient.delete(key);
        if (!result) {
            result = cacheClient.delete(key);
        }
        return result;
    }

    public static Long batchDelete(String[] keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        Boolean delete = false;
        Long delNum = 0L;
        for (String key : keys) {
            delete = cacheClient.delete(key);
            if (delete) {
                delNum++;
            }
        }

        return delNum;
    }

    public static Long batchDelete(List<String> keyList) {
        if (CollectionUtils.isEmpty(keyList)) {
            return null;
        }

        String[] keysString = new String[keyList.size()];
        keyList.toArray(keysString);
        return batchDelete(keysString);
    }

    public static <T extends Serializable> Long lpush(String key, T value) {
        return cacheClient.lpush(key, value);
    }

    public static <T extends Serializable> Long lpushAll(String key, T... values) {
        return cacheClient.lpushAll(key, values);
    }

    public static <T extends Serializable> Long lpushAll(String key, Collection<T> values) {
        return cacheClient.lpushAll(key, values);
    }

    public static <T extends Serializable> Long rpush(String key, T value) {
        return cacheClient.rpush(key, value);
    }

    public static <T extends Serializable> Long rpushAll(String key, T... values) {
        return cacheClient.rpushAll(key, values);
    }

    public static <T extends Serializable> Long rpushAll(String key, Collection<T> values) {
        return cacheClient.rpushAll(key, values);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> lrange(String key, int start, int end) {
        return (List<T>) cacheClient.lrange(key, start, end);
    }

    public static Long llen(String key) {
        return cacheClient.llen(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T rpop(String key) {
        return (T) cacheClient.rpop(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T lpop(String key) {
        return (T) cacheClient.lpop(key);
    }

    public static Long decrBy(String key, long integer) {
        return cacheClient.decrBy(key, integer);
    }

    public static Long incr(String key) {
        return cacheClient.incr(key);
    }

    public static Long incrBy(String key, long integer) {
        return cacheClient.incrBy(key, integer);
    }

    public static Boolean hset(String key, String field, Object value) {
        return cacheClient.hset(key, field, value);
    }

    public static <T> Long hmset(String key, Map<String, T> map) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        for (Entry<String, T> entry : map.entrySet()) {
            cacheClient.hset(key, entry.getKey(), entry.getValue());
        }
        return 0L;
    }

    public static Object hget(String key, String field) {
        return cacheClient.hget(key, field);
    }

    public static Long hincrBy(String key, String field, long value) {
        return cacheClient.hincrBy(key, field, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> hmget(String key, String... fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }
        return (List<T>) cacheClient.hmget(key, fields);
    }

    public static List<String> hmget_str(String key, String... fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }
        List<String> result = new ArrayList<>(fields.length);
        for (String field : fields) {
            if (cacheClient.hgetBytes(key, field) != null) {
                result.add(new String(cacheClient.hgetBytes(key, field)));
            } else {
                result.add(null);
            }
        }
        return result;
    }

    public static Boolean hexists(String key, String field) {
        return cacheClient.hexists(key, field);
    }

    public static Set<String> hkeys(String key) {
        return cacheClient.hkeys(key);
    }

    public static Map<String, Object> hgetAll(String key) {
        return cacheClient.hgetAll(key);
    }

    public static Long hdel(String key, String field) {
        return cacheClient.hdel(key, field);
    }

    public static Boolean publish(final String channel, final String message) {
        return cacheClient.publish(channel, message);
    }

    public static boolean tryGetDistributedLock(String lockKey, String lockValue, int expireTime) {
        return cacheClient.set(lockKey, lockValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
    }

    public static boolean releaseDistributedLock(String lockKey, String lockValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        return cacheClient.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockValue));
    }

    public static Long sadd(String key, Object... values) {
        return cacheClient.sadd(key, values);
    }

    public static <T> Long sadd(String key, List<T> values) {
        return cacheClient.sadd(key, values);
    }

    public static Long sadd(String key, Object value) {
        return cacheClient.sadd(key, value);
    }

    public static <T> Set<T> smembers(String key) {
        return cacheClient.smembers(key);
    }

    public static Boolean sismember(String key, Object value) {
        return cacheClient.sismember(key, value);
    }

    public static Long srem(String key, Object value) {
        return cacheClient.srem(key, value);
    }

    public static Long srem(String key, Object... values) {
        return cacheClient.srem(key, values);
    }

    public static <T> Long srem(String key, List<T> values) {
        return cacheClient.srem(key, values);
    }

    public static Long scard(String key) {
        return cacheClient.scard(key);
    }

    public static Boolean zadd(String key, double score, Object member) {
        return cacheClient.zadd(key, score, member);
    }

    public static Long zadd(String key, Map<Object, Double> scoreMembers) {
        return cacheClient.zadd(key, scoreMembers);
    }

    public static Long zrem(String key, Object value) {
        return cacheClient.zrem(key, value);
    }

    public static <T> List<T> zrange(String key, long start, long end, boolean reverse) {
        return cacheClient.zrange(key, start, end, reverse);
    }

    public static <T> Map<T, Double> zrangeWithScores(String key, long start, long end) {
        return cacheClient.zrangeWithScores(key, start, end);
    }

    public static <T> List<T> zrangeByScore(String key, double min, double max) {
        return cacheClient.zrangeByScore(key, min, max);
    }

    public static <T> Map<T, Double> zrangeByScoreWithScores(String key, double min, double max) {
        return cacheClient.zrangeByScoreWithScores(key, min, max);
    }

    public static Long zcard(String key) {
        return cacheClient.zcard(key);
    }

    public static Double zscore(String key, Object member) {
        return cacheClient.zscore(key, member);
    }

    public static Long zcount(String key, double min, double max) {
        return cacheClient.zcount(key, min, max);
    }

    public static Long zrank(String key, Object member, boolean reverse) {
        return cacheClient.zrank(key, member, reverse);
    }

    public static Double zincrby(String key, double increment, Object member) {
        return cacheClient.zincrby(key, increment, member);
    }
}
