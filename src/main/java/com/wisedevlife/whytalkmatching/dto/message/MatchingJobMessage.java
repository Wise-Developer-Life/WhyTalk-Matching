package com.wisedevlife.whytalkmatching.dto.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchingJobMessage {
    private String userId;
}
