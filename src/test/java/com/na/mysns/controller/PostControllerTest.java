package com.na.mysns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.na.mysns.controller.request.PostCreateRequest;
import com.na.mysns.controller.request.UserJoinRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.na.mysns.controller.request.UserJoinRequest;
import com.na.mysns.controller.request.UserLoginRequest;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void 포스트작성() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO: add reqeust body
                        .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body)))
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithAnonymousUser
    void 포스트작성시_로그인하지않은경우() throws Exception {

        String title = "title";
        String body = "body";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO: add reqeust body
                        .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body)))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
}
