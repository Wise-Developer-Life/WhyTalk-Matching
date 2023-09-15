package com.wisedevlife.whytalkmatching.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RangePair<T extends Comparable<T>> {
    private T lowerBound;
    private T upperBound;
}
