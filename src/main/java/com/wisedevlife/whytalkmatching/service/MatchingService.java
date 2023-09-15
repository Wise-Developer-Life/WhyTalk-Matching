package com.wisedevlife.whytalkmatching.service;

import com.wisedevlife.whytalkmatching.model.MatchingFilter;
import com.wisedevlife.whytalkmatching.model.UserModel;
import java.util.List;

public interface MatchingService {
    void startMatchingProcess(String userId, MatchingFilter filter);

    List<UserModel> matchUser(String userId);
}
