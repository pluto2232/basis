package com.pluto.redismq.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @author cheny
 * @date 2023年02月20日 11:53
 */
@Component
public class RedisMqUtil {


    /**
     * TODO:
     *   动态源(db) 切换
     *   不需要强制刷新 重置
     *   设置对应 redisTemplate map<db, redisTemplate>
     */

    @Autowired
    private StringRedisTemplate redisTemplate;

    public StringRedisTemplate getRedisTemplate(int db) {
        changeDB(db);
        return redisTemplate;
    }

    /**
     * 添加消息
     * @param topic 主题
     * @param obj 数据
     */
    public void addToMq(int db,String topic, Object obj) {
        String str = obj instanceof String ? (String) obj : JSON.toJSONString(obj);

        //切换指定redis db 配置
        changeDB(db);

        redisTemplate.opsForList().leftPush(topic, str);
    }


    private void changeDB(int db) {
        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        if (connectionFactory != null && db != connectionFactory.getDatabase()) {
            //切换DB
            connectionFactory.setDatabase(db);
            //是否允许多个线程操作共用同一个缓存连接，默认 true，false 时每个操作都将开辟新的连接
            connectionFactory.setShareNativeConnection(false);
            //重置db后刷新连接 RedisClient.redisUrl
            connectionFactory.afterPropertiesSet();
            redisTemplate.setConnectionFactory(connectionFactory);
            //重置连接
            connectionFactory.resetConnection();
        }
    }

}
