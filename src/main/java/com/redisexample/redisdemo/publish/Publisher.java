package com.redisexample.redisdemo.publish;

import com.redisexample.redisdemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Publisher {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ChannelTopic channelTopic;

    @PostMapping("/publish")
    public String publish(@RequestBody Student studnet) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), studnet.toString());
        return "event published";
    }
}
