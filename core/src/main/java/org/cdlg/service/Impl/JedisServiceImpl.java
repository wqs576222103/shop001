package org.cdlg.service.Impl;

import org.cdlg.service.JedisService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Auther: wqs
 * @Date: 2018/8/18 0018 21:00
 * @Description: I LOVE IT？
 */
public class JedisServiceImpl implements JedisService {
    private JedisPool jedisPool;

    private Jedis getJedis(){
        return  jedisPool.getResource();
    }
    @Override
    public String get(String key) {
        Jedis jedis=getJedis();
       String string= jedis.get(key);
        jedis.close();
        return string;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis=getJedis();
        String string=jedis.set(key,value);
        jedis.close();
        return string;
    }

    @Override
    public long del(String key) {
        Jedis jedis=getJedis();
        long status=jedis.del(key);
        jedis.close();
        return status;
    }

    @Override
    public long incr(String key) {
        Jedis jedis=getJedis();
        long status=jedis.incr(key);
        jedis.close();
        return status;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis=getJedis();
        String hstring=jedis.hget(hkey,key);
        jedis.close();
        return hstring;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis=getJedis();
        long status=jedis.hset(hkey,key,value);
        jedis.close();
        return status;
    }

    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis=getJedis();
        long status=jedis.hdel(hkey,key);
        jedis.close();
        return status;
    }

    @Override
    public long expire(String key, int time) {
        Jedis jedis=getJedis();
        long status=jedis.expire(key,time);
        jedis.close();
        return status;
    }

    @Override
    public long ttl(String key) {
        Jedis jedis=getJedis();
        long status=jedis.ttl(key);
        jedis.close();
        return status;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
}
