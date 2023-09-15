package com.wisedevlife.whytalkmatching.service.implement;

import com.wisedevlife.whytalkmatching.common.constants.MqConstant;
import com.wisedevlife.whytalkmatching.common.constants.RedisConstant;
import com.wisedevlife.whytalkmatching.common.utils.MessageQueueUtils;
import com.wisedevlife.whytalkmatching.common.utils.RedisUtils;
import com.wisedevlife.whytalkmatching.dto.message.MatchingJobMessage;
import com.wisedevlife.whytalkmatching.dto.request.MatchingUserMeta;
import com.wisedevlife.whytalkmatching.model.MatchingFilter;
import com.wisedevlife.whytalkmatching.model.UserModel;
import com.wisedevlife.whytalkmatching.service.MatchingService;
import com.wisedevlife.whytalkmatching.service.UserService;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {
    private final RedisUtils redisUtils;
    private final MessageQueueUtils messageQueueUtils;

    private final UserService userService;

    @Override
    public void startMatchingProcess(String userId, MatchingFilter filter) {
        Instant startMatchingTime = Instant.now();
        long startMatchingTimestamp = startMatchingTime.getEpochSecond();

        UserModel user = userService.getUserDetail(userId);

        MatchingUserMeta matchingUserMeta =
                MatchingUserMeta.builder()
                        .userProfile(user)
                        .matchingFilter(filter)
                        .startMatchingTimestamp(startMatchingTimestamp)
                        .build();

        redisUtils.setObject(RedisConstant.getUserProfileKey(userId), matchingUserMeta);

        redisUtils.addValueToSortedSet(
                RedisConstant.MATCHING_POOL_SET, userId, (double) startMatchingTimestamp);
        log.info(
                "put user {} into matching pool with score {}",
                userId,
                (double) startMatchingTimestamp);

        MatchingJobMessage matchingJobMessage = MatchingJobMessage.builder().userId(userId).build();

        messageQueueUtils.enQueueJob(MqConstant.MQ_MATCHING_JOB_KEY, matchingJobMessage);
        log.info("enqueue user {}", userId);
    }

    @Override
    public List<UserModel> matchUser(String currentUserId) {
        if (!redisUtils.isSortedSetContainsKey(RedisConstant.MATCHING_POOL_SET, currentUserId)) {
            throw new RuntimeException(
                    String.format("User %s is not in matching status", currentUserId));
        }

        MatchingUserMeta matchingMessageOfCurrentUser =
                redisUtils.getObject(RedisConstant.getUserProfileKey(currentUserId));

        if (matchingMessageOfCurrentUser == null) {
            throw new RuntimeException(
                    String.format("User %s is not in matching status", currentUserId));
        }

        Set<String> usersInMatching =
                redisUtils.getAllValuesFromSortedSetDesc(RedisConstant.MATCHING_POOL_SET);

        UserModel currentUser = matchingMessageOfCurrentUser.getUserProfile();
        MatchingFilter filterOfCurrentUser = matchingMessageOfCurrentUser.getMatchingFilter();

        UserModel matchedUser = null;
        for (String userInMatching : usersInMatching) {
            if (userInMatching.equals(currentUserId)) {
                continue;
            }

            MatchingUserMeta matchingMessageOfOtherUser =
                    redisUtils.getObject(RedisConstant.getUserProfileKey(userInMatching));

            if (matchingMessageOfOtherUser == null) {
                log.info("user {} is not in matching status", userInMatching);
                continue;
            }

            UserModel otherUser = matchingMessageOfOtherUser.getUserProfile();
            MatchingFilter filterOfOtherUser = matchingMessageOfOtherUser.getMatchingFilter();

            if (isMatched(currentUser, filterOfCurrentUser, otherUser, filterOfOtherUser)) {
                matchedUser = otherUser;
            }
        }

        if (matchedUser != null) {
            redisUtils.removeValueFromSortedSet(RedisConstant.MATCHING_POOL_SET, matchedUser);
            redisUtils.deleteKey(RedisConstant.getUserProfileKey(matchedUser.getUserId()));
        }

        return matchedUser == null ? List.of() : List.of(currentUser, matchedUser);
    }

    private boolean isMatched(
            UserModel user1, MatchingFilter filter1, UserModel user2, MatchingFilter filter2) {
        return satisfyFilter(user1, filter2) && satisfyFilter(user2, filter1);
    }

    private boolean satisfyFilter(UserModel user, MatchingFilter filter) {
        boolean isGenderSatisfied = filter.getGenderOptions().contains(user.getGender());

        int userAge = user.getAge();

        boolean isAgeSatisfied = filter.getAgeRange().contains(userAge);
        return isGenderSatisfied && isAgeSatisfied;
    }
}
