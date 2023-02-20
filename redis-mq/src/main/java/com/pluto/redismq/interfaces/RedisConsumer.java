package com.pluto.redismq.interfaces;

/**
 * redisMq 消费者
 * @author cheny
 * @date 2023年02月20日 11:46
 */
public interface RedisConsumer {

    /**
     * 消费方法，消费者统一实现
     * @param message
     */
    void deal(String message);

}
