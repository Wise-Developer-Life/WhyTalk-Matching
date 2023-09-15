package com.wisedevlife.whytalkmatching.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    public static final String USER_SERVICE_CLIENT = "userServiceClient";

    @Bean(name = USER_SERVICE_CLIENT)
    public WebClient userServiceClient() {
        return WebClient.builder()
                .baseUrl("http://whytalk-user:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }
}
