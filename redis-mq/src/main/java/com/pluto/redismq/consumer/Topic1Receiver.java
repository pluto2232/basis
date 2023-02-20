package com.pluto.redismq.consumer;

import com.pluto.redismq.annotation.MqConsumer;
import com.pluto.redismq.interfaces.RedisConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cheny
 * @date 2023年02月20日 14:55
 */
@MqConsumer(topic = "topic1")
public class Topic1Receiver implements RedisConsumer {

    private static final Logger log = LoggerFactory.getLogger(Topic1Receiver.class);

    @Override
    public void deal(String message) {

        log.info("topic1收到信息:" + message);
    }

}
