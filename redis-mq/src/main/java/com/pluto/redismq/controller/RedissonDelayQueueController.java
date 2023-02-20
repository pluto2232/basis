package com.pluto.redismq.controller;

import com.pluto.redismq.delayQueue.RedissonDelayQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cheny
 * @date 2023年02月20日 17:06
 */
@RestController
public class RedissonDelayQueueController {

    @Resource
    private RedissonDelayQueue redissonDelayQueue;

    @GetMapping("/add")
    public void addTask(@RequestParam("task") String task) {

        redissonDelayQueue.offerTask(task, 5);
    }


}
