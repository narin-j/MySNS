package com.na.mysns.controller.response;

import com.na.mysns.model.User;
import com.na.mysns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private String token;
}
