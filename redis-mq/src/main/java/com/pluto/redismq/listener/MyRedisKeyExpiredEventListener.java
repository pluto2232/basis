package com.pluto.redismq.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

/**
 * 监听 RedisKeyExpiredEvent 消息过期事件
 *
 * 观察者模式
 *  ApplicationListener: Spring事件机制 抽象类<ApplicationEvent></>类配合
 *  <ApplicationContext></> 调用 publishEvent()方法 对应的Bean会被触发
 *
 * @author cheny
 * @date 2023年02月20日 15:45
 */
@Component
public class MyRedisKeyExpiredEventListener implements ApplicationListener<RedisKeyExpiredEvent> {

    @Override
    public void onApplicationEvent(RedisKeyExpiredEvent event) {
        byte[] body = event.getSource();
        System.out.println("获取到延迟消息：" + new String(body));
    }

}
