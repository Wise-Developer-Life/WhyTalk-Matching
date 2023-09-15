package com.wisedevlife.whytalkmatching.common.utils;

import com.wisedevlife.whytalkmatching.common.constants.MqConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageQueueUtils {
    private final RabbitTemplate rabbitTemplate;

    public void enQueueJob(String jobKey, Object messagePayload) {
        rabbitTemplate.convertAndSend(MqConstant.MQ_JOB_EXCHANGE, jobKey, messagePayload);
    }

    public void publishMessageToExchange(
            String exchangeName, String routingKey, Object messagePayload) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, messagePayload);
    }
}
