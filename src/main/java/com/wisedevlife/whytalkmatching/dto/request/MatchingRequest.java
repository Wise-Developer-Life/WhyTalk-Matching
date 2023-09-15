package com.wisedevlife.whytalkmatching.dto.request;

import com.wisedevlife.whytalkmatching.common.dto.RangePair;
import com.wisedevlife.whytalkmatching.common.enums.SexEnum;
import java.util.List;

public record MatchingRequest(
        String userId, List<SexEnum> genderOptions, RangePair<Integer> ageRange) {}
