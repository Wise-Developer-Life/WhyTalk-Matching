package com.wisedevlife.whytalkmatching.common.constants;

public class RedisConstant {
    private RedisConstant() {}

    public static final String MATCHING_POOL_SET = "matching_pool";
    public static final String MATCHING_POOL_PREFIX = "matching_pool_users";

    public static String getUserProfileKey(String userId) {
        return MATCHING_POOL_PREFIX + ":" + userId;
    }
}
