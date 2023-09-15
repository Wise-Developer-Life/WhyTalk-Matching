package com.wisedevlife.whytalkmatching.dto.request;

import com.wisedevlife.whytalkmatching.model.MatchingFilter;
import com.wisedevlife.whytalkmatching.model.UserModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchingUserMeta {
    UserModel userProfile;
    MatchingFilter matchingFilter;
    long startMatchingTimestamp;
}
