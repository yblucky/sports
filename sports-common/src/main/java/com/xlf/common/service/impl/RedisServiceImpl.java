package com.xlf.common.service.impl;

import com.xlf.common.service.RedisService;
import com.xlf.common.util.LogUtils;
import com.xlf.common.util.SerializeUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

/**
 * redis 数据库操作类
 *
 * @author qsy
 * @version v1.0
 * @date 2016年11月23日
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private JedisPool jedisPool;

    @Override
    public String putString(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.setex (key, seconds, value);
        } catch (Exception e) {
            LogUtils.error (e.getMessage (), e);
        } finally {
            close (jedis);
        }
        return res;
    }

    @Override
    public String getString(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            value = jedis.get (key);
        } catch (Exception e) {
            LogUtils.error (e.getMessage (), e);
        } finally {
            close (jedis);
        }
        return value;
    }

    @Override
    public String putObj(String key, Object value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            res = jedis.set (key.getBytes (), SerializeUtil.serialize (value));
            jedis.expire (key.getBytes (), seconds);
        } catch (Exception e) {
            LogUtils.error (e.getMessage (), e);
        } finally {
            close (jedis);
        }
        return res;
    }

    @Override
    public Object getObj(String key) {
        Jedis jedis = null;
        byte[] byteValue = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            byteValue = jedis.get (key.getBytes ());
            if (null == byteValue || byteValue.length < 1) {
                return null;
            }
        } catch (Exception e) {
            LogUtils.error (e.getMessage (), e);
        } finally {
            close (jedis);
        }
        return SerializeUtil.unserialize (byteValue);
    }

    /**
     * 释放连接
     *
     * @param jedis
     */
    private void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close ();
        }
    }

    /**
     * 获取Redis实例
     *
     * @return redis实例
     */
    private Jedis getJedis() {
        if (jedisPool != null) {
            return jedisPool.getResource ();
        } else {
            return null;
        }
    }

    /**
     * 更新时间
     */
    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.expire (key, seconds);
        } catch (Exception e) {
            LogUtils.error (e.getMessage (), e);
        } finally {
            close (jedis);
        }
        return res;
    }

    @Override
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.del (keys);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return 0L;
    }


    @Override
    public Long rpush(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.rpush (key, value);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return 0L;
    }

    @Override
    public byte[] lpop(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.lpop (key);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return null;
    }

    @Override
    public byte[] getList(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.getrange (key, 0, 1000);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return null;
    }

    @Override
    public Long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.sadd (key, value);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return 0l;
    }

    @Override
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.smembers (key);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return Collections.emptySet ();
    }

    @Override
    public Long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.scard (key);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return 0l;
    }

    @Override
    public Long hset(String key,String field,String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.hset (key,field,value);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return 0l;
    }

    @Override
    public String hget(String key,String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis ();// 获得jedis实例
            return jedis.hget(key,field);
        } catch (Exception ex) {
            LogUtils.error (ex.getMessage (), ex);
        } finally {
            close (jedis);
        }
        return null;
    }
}
