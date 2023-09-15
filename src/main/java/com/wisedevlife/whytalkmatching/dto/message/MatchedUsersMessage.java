package com.wisedevlife.whytalkmatching.dto.message;

import com.wisedevlife.whytalkmatching.model.UserModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchedUsersMessage {
    private UserModel user1;
    private UserModel user2;
}
