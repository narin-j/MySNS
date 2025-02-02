package com.na.mysns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 로그인을 할 때 리퀘스트 바디로 사용할 클래스
// 로그인/회원가입 목적이 다르므로 클래스를 분리해줌(선호도 차이)
@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private String name;
    private String password;
}
