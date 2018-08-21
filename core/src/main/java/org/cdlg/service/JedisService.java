package org.cdlg.service;

/**
 * @Auther: wqs
 * @Date: 2018/8/18 0018 21:00
 * @Description: I LOVE IT？
 */
public interface JedisService {
    //String
    public String get(String key);
    public String  set(String key, String value);
    public  long del(String key);
    public  long incr(String key);

    //hash
    public String hget(String hkey, String key);
    public  long hset(String hkey, String key, String value);
    public long hdel(String hkey, String key);

    //list

    //set

    //setSort



    public  long expire(String key, int time);//过期时间
    public  long ttl(String key);//查看剩余时间



}
