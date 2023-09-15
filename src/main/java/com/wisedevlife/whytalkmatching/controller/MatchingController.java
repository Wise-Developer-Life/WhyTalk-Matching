package com.wisedevlife.whytalkmatching.controller;

import com.wisedevlife.whytalkmatching.common.helper.ResponseHandler;
import com.wisedevlife.whytalkmatching.dto.request.MatchingRequest;
import com.wisedevlife.whytalkmatching.dto.response.ReturnResponse;
import com.wisedevlife.whytalkmatching.model.MatchingFilter;
import com.wisedevlife.whytalkmatching.service.MatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matching")
@RequiredArgsConstructor
@Tag(name = "Matching API", description = "start to match user")
public class MatchingController {
    private final MatchingService matchingService;

    @PostMapping()
    @Operation(summary = "Start to match user")
    public ResponseEntity<ReturnResponse<Object>> startMatchUserProcess(
            @RequestBody MatchingRequest matchingRequest) {
        String currentUserId = matchingRequest.userId();
        Range<Integer> ageRange =
                Range.closed(
                        matchingRequest.ageRange().getLowerBound(),
                        matchingRequest.ageRange().getUpperBound());

        MatchingFilter filter =
                MatchingFilter.builder()
                        .ageRange(ageRange)
                        .genderOptions(matchingRequest.genderOptions())
                        .build();

        this.matchingService.startMatchingProcess(currentUserId, filter);

        return ResponseHandler.success();
    }
}
