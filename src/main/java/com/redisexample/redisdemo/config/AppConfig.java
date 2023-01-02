package com.redisexample.redisdemo.config;

import com.redisexample.redisdemo.subscriber.Receiver;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
//@EnableRedisRepositories
public class AppConfig {

    //There re two ways of creating driver, one is jedis another is lettuce,
    //lettuce is bit lazy in intialization so it creates beans  lazily
    @Bean
    RedisConnectionFactory jedisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
     //   template.setValueSerializer(new JdkSerializationRedisSerializer());
        //for message from channel
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    //For setting host and port
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConFactory
//                = new JedisConnectionFactory();
//        jedisConFactory.setHostName("localhost");
//        jedisConFactory.setPort(6379);
//        return jedisConFactory;
//    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("pubsub:mytopic");
    }

    //pass instance of listener to listen the messages
    @Bean
    public MessageListenerAdapter messagelisterner() {
        return new MessageListenerAdapter(new Receiver());
    }

    //need one container to manage this listner, topic and connection factory
    @Bean
    public RedisMessageListenerContainer getContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory());
        redisMessageListenerContainer.addMessageListener(messagelisterner(), topic());
        return redisMessageListenerContainer;
    }

}
