package jclipper.springboot.redis.lettuce;

import io.lettuce.core.internal.LettuceLists;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis集群客户端
 */
public class LettuceClusterClient implements CacheLettuceClient {

    private RedisTemplate<String, Object> redisTemplate;

    public LettuceClusterClient(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void flushAll() {
        throw new NotSupportOperationException();
    }

    @Override
    public Long ttl(String key) {
        throw new NotSupportOperationException();
    }

    @Override
    public Set<String> keys(String pattern) {
        throw new NotSupportOperationException();
    }

    @Override
    public Long expire(String key, int seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        return Long.valueOf(seconds);
    }

    @Override
    public Boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public <T> Boolean set(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }

//        Pair<byte[], byte[]> t = toKeyAndValue(key, value);
//        return redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.set(t.getKey(), t.getValue());
//            }
//        });
    }

    @Override
    public <T> Boolean set(String key, int seconds, T value) {
        try {
            redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
//        Pair<byte[], byte[]> t = toKeyAndValue(key, value);
//        return redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.setEx(t.getKey(), seconds, t.getValue());
//            }
//        });
    }

    @Override
    public Boolean set(String key, String value, String nxxx, String expx, long timeds) {
       /* Pair<byte[],byte[]> t = toKeyAndValue(key,value);
        return jc.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return  connection.setNX(t.getKey(),  t.getValue());
            }
        });*/
        return null;
    }

    @Override
    public Boolean eval(String script, List<String> keys, List<String> args) {
        DefaultRedisScript<Boolean> getRedisScript = new DefaultRedisScript<Boolean>();
        getRedisScript.setResultType(Boolean.class);
        getRedisScript.setScriptText(script);
        return redisTemplate.execute(getRedisScript, keys, args);
    }

    @Override
    public List<Object> batchSet(List<String> keys, List<Object> values) {
        throw new NotSupportOperationException();
    }

    @Override
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public byte[] getBytes(String key) {
        byte[] rawKey = ((RedisSerializer) redisTemplate.getKeySerializer()).serialize(key);
        return redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(rawKey));
    }

    @Override
    public <T> List<T> batchGet(String[] keys) {
        throw new NotSupportOperationException();
    }

    @Override
    public <T> T get(String key, Class<T> t) {
        return get(key);
    }

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void batchDelete(String[] keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(Arrays.asList(keys));
            }
        }
    }

    @Override
    public <T> Long lpush(String key, T value) {
        try {
            return redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public <T> Long lpushAll(String key, T... values) {
        try {
            return redisTemplate.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public <T> Long lpushAll(String key, Collection<T> values) {
        try {
            return redisTemplate.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public <T> Long rpush(String key, T value) {
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public <T> Long rpushAll(String key, T... values) {
        try {
            return redisTemplate.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public <T> Long rpushAll(String key, Collection<T> values) {
        try {
            return redisTemplate.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public <T> List<T> lrange(String key, int start, int end) {
        try {
            return (List<T>) redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long llen(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Override
    public <T> T rpop(String key) {
        try {
            return (T) redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> T lpop(String key) {
        try {
            return (T) redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long decrBy(String key, long integer) {
        try {
            return redisTemplate.opsForValue().decrement(key, integer);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long incr(String key) {
        try {
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long incrBy(String key, long integer) {
        try {
            return redisTemplate.opsForValue().increment(key, integer);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean hset(String key, String field, Object value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public <T> Boolean hmset(String key, Map<String, T> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object hget(String key, String field) {
        try {
            return redisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public byte[] hgetBytes(String key, String field) {
        throw new NotSupportOperationException();
    }

    @Override
    public Long hincrBy(String key, String field, long integer) {
        try {
            return redisTemplate.opsForHash().increment(key, field, integer);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Object> hmget(String key, String... fields) {
        try {
            return redisTemplate.opsForHash().multiGet(key, LettuceLists.newList(fields));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> hmget_str(String key, String... fields) {
        throw new NotSupportOperationException();
    }

    @Override
    public Boolean hexists(String key, String field) {
        try {
            return redisTemplate.opsForHash().get(key, field) != null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Set<String> hkeys(String key) {
        try {
            HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
            return hash.keys(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> hgetAll(String key) {
        try {
            HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
            return hash.entries(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long hdel(String key, String field) {
        try {
            return redisTemplate.opsForHash().delete(key, field);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean publish(String channel, String message) {
        try {
            redisTemplate.convertAndSend(channel, message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public <T> Long sadd(String key, List<T> values) {
        if (values == null) {
            return 0L;
        }
        Object[] objects = values.toArray(new Object[0]);
        return sadd(key, objects);
    }

    @Override
    public Long sadd(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long sadd(String key, Object value) {
        try {
            return redisTemplate.opsForSet().add(key, value);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> Set<T> smembers(String key) {
        try {
            Set<Object> members = redisTemplate.opsForSet().members(key);
            if (members == null) {
                return null;
            }
            return members.stream().map(o -> (T) o).collect(Collectors.toSet());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean sismember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long srem(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> Long srem(String key, List<T> values) {
        if (values == null) {
            return 0L;
        }
        Object[] objects = values.toArray(new Object[0]);
        return srem(key, objects);
    }

    @Override
    public Long srem(String key, Object value) {
        try {
            return redisTemplate.opsForSet().remove(key, value);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long scard(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean zadd(String key, double score, Object member) {
        try {
            return redisTemplate.opsForZSet().add(key, member, score);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long zadd(String key, Map<Object, Double> scoreMembers) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
            scoreMembers.forEach((k, v) -> {
                tuples.add(new DefaultTypedTuple(k, v));
            });
            return redisTemplate.opsForZSet().add(key, tuples);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Double zincrby(String key, double increment, Object member) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, member, increment);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long zrem(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().remove(key, value);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> List<T> zrange(String key, long start, long end, boolean reverse) {
        try {
            Set<Object> range = null;
            if (reverse) {
                range = redisTemplate.opsForZSet().reverseRange(key, start, end);
            } else {
                range = redisTemplate.opsForZSet().range(key, start, end);
            }
            if (range != null) {
                return range.stream().map(o -> (T) o).collect(Collectors.toList());
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> Map<T, Double> zrangeWithScores(String key, long start, long end) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeWithScores(key, start, end);
            Map<T, Double> map = new HashMap<>();
            ;
            if (tuples != null) {
                tuples.forEach(t -> map.put((T) t.getValue(), t.getScore()));
            }
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> List<T> zrangeByScore(String key, double min, double max) {
        throw new NotSupportOperationException();
    }

    @Override
    public <T> Map<T, Double> zrangeByScoreWithScores(String key, double min, double max) {
        throw new NotSupportOperationException();
    }

    @Override
    public Long zcard(String key) {
        throw new NotSupportOperationException();
    }

    @Override
    public Double zscore(String key, Object member) {
        throw new NotSupportOperationException();
    }

    @Override
    public Long zcount(String key, double min, double max) {
        throw new NotSupportOperationException();
    }

    @Override
    public Long zrank(String key, Object member, boolean reverse) {
        throw new NotSupportOperationException();
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        throw new NotSupportOperationException();
    }

    public <T> Pair<byte[], byte[]> toKeyAndValue(String key, T value) {
        byte[] rawKey = ((RedisSerializer) redisTemplate.getKeySerializer()).serialize(key);
        byte[] rawValue = ((RedisSerializer) redisTemplate.getValueSerializer()).serialize(value);
        return Pair.of(rawKey, rawValue);
    }
}
