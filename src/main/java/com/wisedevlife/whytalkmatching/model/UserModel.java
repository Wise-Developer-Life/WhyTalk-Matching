package com.wisedevlife.whytalkmatching.model;

import com.wisedevlife.whytalkmatching.common.enums.SexEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    String userId;
    String name;
    SexEnum gender;
    int age;
}
