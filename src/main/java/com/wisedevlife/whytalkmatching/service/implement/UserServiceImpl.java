package com.wisedevlife.whytalkmatching.service.implement;

import com.wisedevlife.whytalkmatching.common.config.WebClientConfiguration;
import com.wisedevlife.whytalkmatching.model.UserModel;
import com.wisedevlife.whytalkmatching.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserServiceImpl implements UserService {
    private final WebClient userServiceClient;

    public UserServiceImpl(
            @Qualifier(WebClientConfiguration.USER_SERVICE_CLIENT) WebClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    // TODO: use more smart way to get user detail
    @Override
    public UserModel getUserDetail(String userId) {
        // FIXME: fix response type
        return userServiceClient
                .get()
                .uri("/user_profile/{userId}", userId)
                .retrieve()
                .bodyToMono(UserModel.class)
                .block();
    }
}
