package com.na.mysns.controller;

import com.na.mysns.controller.request.UserJoinRequest;
import com.na.mysns.controller.request.UserLoginRequest;
import com.na.mysns.controller.response.Response;
import com.na.mysns.controller.response.UserJoinResponse;
import com.na.mysns.controller.response.UserLoginResponse;
import com.na.mysns.model.User;
import com.na.mysns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getUserName(), request.getPassword());
        // UserJoinResponse response = UserJoinResponse.fromUser(user);

        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUserName(), request.getPassword());
        return Response.success(new UserLoginResponse(token ));
    }
}
