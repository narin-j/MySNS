package com.na.mysns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 회원가입을 할 때 리퀘스트 바디로 사용할 클래스
@Getter
@AllArgsConstructor
public class UserJoinRequest {
    private String name;
    private String password;
}
