package com.na.mysns.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.na.mysns.controller.request.UserJoinRequest;
import com.na.mysns.controller.request.UserLoginRequest;
import com.na.mysns.exception.ErrorCode;
import com.na.mysns.exception.SnsApplicationException;
import com.na.mysns.model.User;
import com.na.mysns.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // request object 넘겨주기
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @Test
    public void 회원가입() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO: mocking
        when(userService.join(userName, password)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    // TODO: add reqeust body
                    .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입시_이미_회원가입된_userName_존재() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO: mocking
        when(userService.join(userName, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATE_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO: add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))

                )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void 로그인() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO: mocking
        when(userService.login(userName, password)).thenReturn("test_tkn");

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO: add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void 로그인시_회원가입이_안된_userName입력() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO: mocking
        when(userService.login(userName, password)).thenThrow(new SnsApplicationException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO: add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 로그인시_틀린_password() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO: mocking
        when(userService.login(userName, password)).thenThrow(new SnsApplicationException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO: add reqeust body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
