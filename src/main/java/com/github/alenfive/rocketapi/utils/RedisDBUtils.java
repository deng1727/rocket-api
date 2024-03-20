package com.github.alenfive.rocketapi.utils;

import com.github.alenfive.rocketapi.entity.DBConfig;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Iterator;
import java.util.Set;

public class RedisDBUtils {
    public static RedisTemplate getRedisTemplate(DBConfig config){
        RedisTemplate redisTemplate =  new RedisTemplate();

        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();

        String url = config.getUrl();
        String replace = url.replace("redis:/", "");
        String[] splits = replace.split("/");
        String[] localhosts = splits[1].split(":");

        rsc.setPort(Integer.valueOf(localhosts[1]));
        rsc.setPassword(config.getPassword());
        rsc.setHostName(localhosts[0]);
        rsc.setDatabase(Integer.valueOf(splits[2]));

        JedisConnectionFactory fac = new JedisConnectionFactory(rsc);


        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(fac);
        redisTemplate.afterPropertiesSet();


        return redisTemplate;
    }

    public static void main(String[] args) {
        RedisTemplate<Object, Object> redisTemplate =  new RedisTemplate();

        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();



        rsc.setPort(6379);
        rsc.setPassword("IyY1NA3Zre76542M");
        rsc.setHostName("r-3ns20c32d1acd834.redis.rds.aliyuncs.com");
        rsc.setDatabase(7);

        JedisConnectionFactory fac = new JedisConnectionFactory(rsc);


        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(fac);
        redisTemplate.afterPropertiesSet();
        Set keys = redisTemplate.keys("*");

        Iterator iterator = keys.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
