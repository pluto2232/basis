package com.pluto.redismq.controller;

import com.pluto.redismq.utils.RedisMqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cheny
 * @date 2023年02月20日 13:57
 */
@RestController
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Resource
    private RedisMqUtil redisMqUtil;

    @RequestMapping("/topic1")
    public void addOne(String message) {
        redisMqUtil.addToMq(5,"topic1", message);
    }

    @RequestMapping("/topic2")
    public void addOne1(String message) {
        redisMqUtil.addToMq(5,"topic2", message);
    }

}
