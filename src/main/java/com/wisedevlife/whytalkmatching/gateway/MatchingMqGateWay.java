package com.wisedevlife.whytalkmatching.gateway;

import com.wisedevlife.whytalkmatching.common.constants.MqConstant;
import com.wisedevlife.whytalkmatching.common.utils.MessageQueueUtils;
import com.wisedevlife.whytalkmatching.dto.message.MatchedUsersMessage;
import com.wisedevlife.whytalkmatching.dto.message.MatchingJobMessage;
import com.wisedevlife.whytalkmatching.model.UserModel;
import com.wisedevlife.whytalkmatching.service.MatchingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingMqGateWay {
    private final MatchingService matchingService;
    private final MessageQueueUtils messageQueueUtils;

    // TODO: use ack to ack message
    @RabbitListener(queues = MqConstant.MQ_MATCHING_JOB_QUEUE)
    public void matchingUser(MatchingJobMessage message) {
        List<UserModel> matchedUsers = matchingService.matchUser(message.getUserId());

        if (matchedUsers.size() != 2) {
            log.info("No matched user found for user {}", message.getUserId());
            // TODO: design requeue message mechanism
            return;
        }

        UserModel user1 = matchedUsers.get(0);
        UserModel user2 = matchedUsers.get(1);

        MatchedUsersMessage matchedUsersMessage =
                MatchedUsersMessage.builder().user1(user1).user2(user2).build();

        messageQueueUtils.publishMessageToExchange(
                MqConstant.MQ_MATCHING_EXCHANGE,
                MqConstant.MATCHING_COMPLETE_ROUTING_KEY,
                matchedUsersMessage);
    }
}
