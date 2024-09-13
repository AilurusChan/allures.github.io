package com.ydlclass.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ydlclass.configuration.CustomObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author itnanls(微信)
 * 我们的服务： 一路陪跑，顺利就业
 */
@Component
@Slf4j
public class RedisTemplate {

    @Resource
    private JedisPool jedisPool;

    @Resource
    private CustomObjectMapper objectMapper;

    // 保存字符串类型的数据
    public String set(String key,String value,Long expire){
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {
            returnValue = jedis.setex(key, expire, value);
        } catch (JedisException jedisException){
            log.error("redis execution error!",jedisException);
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return returnValue;
    }

    // 保存字符串类型的数据
    public String get(String key){
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {
            returnValue = jedis.get(key);
        } catch (JedisException jedisException){
            log.error("redis execution error!",jedisException);
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return returnValue;
    }

    // 保存字符串类型的数据
    public long remove(String ...key){
        Jedis jedis = jedisPool.getResource();
        long del = 0L;
        try {
            del = jedis.del(key);
        } catch (JedisException jedisException){
            log.error("redis execution error!",jedisException);
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return del;
    }

    // 将对象以序列化的方式存在redis，json
    // 保存字符串类型的数据
    public String setObject(String key,Object value,Long expire){
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;
        try {
            // 先序列化成json
            String jsonValue = objectMapper.writeValueAsString(value);
            if(expire <= 0){
                returnValue = jedis.set(key, jsonValue);
            } else {
                returnValue = jedis.setex(key, expire, jsonValue);
            }
        } catch (JedisException | JsonProcessingException exception){
            log.error("redis execution error!",exception);
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return returnValue;
    }

    // 将对象以序列化的方式存在redis，json
    // 保存字符串类型的数据
    public <T> T getObject(String key, TypeReference<T> typeReference){
        Jedis jedis = jedisPool.getResource();
        T object = null;
        try {
            // 从redis中获取
            String returnValue = jedis.get(key);
            object = objectMapper.readValue(returnValue,typeReference);
        } catch (JedisException | JsonProcessingException exception){
            log.error("redis execution error!",exception);
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return object;
    }


    // 增加key的时间
    public long expire(String key,Long expire){
        Jedis jedis = jedisPool.getResource();
        long exp = -1L;
        try {
            // 从redis中获取
            exp  = jedis.expire(key, expire);
        } catch (JedisException exception){
            log.error("redis execution error!",exception);
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return exp;
    }

    // 查询特定的key
    public Set<String> keys(String pattern){
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = null;
        try {
            // 从redis中获取
            keys = jedis.keys(pattern);
        } catch (JedisException exception){
            log.error("redis execution error!",exception);
            jedisPool.returnBrokenResource(jedis);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return keys;
    }


}
