package com.wisedevlife.whytalkmatching.common.config;

import com.wisedevlife.whytalkmatching.common.constants.MqConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfiguration {
    @Bean(name = MqConstant.MQ_JOB_EXCHANGE)
    public DirectExchange jobExchange() {
        return new DirectExchange(MqConstant.MQ_JOB_EXCHANGE);
    }

    @Bean(name = MqConstant.MQ_MATCHING_EXCHANGE)
    public TopicExchange matchingExchange() {
        return new TopicExchange(MqConstant.MQ_MATCHING_EXCHANGE);
    }

    @Bean(name = MqConstant.MQ_MATCHING_JOB_QUEUE)
    public Queue matchingJobQueue() {
        return new Queue(MqConstant.MQ_MATCHING_JOB_QUEUE);
    }
}
