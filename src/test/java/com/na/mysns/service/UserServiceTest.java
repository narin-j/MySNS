package com.na.mysns.service;

import com.na.mysns.exception.SnsApplicationException;
import com.na.mysns.fixture.UserEntityFixture;
import com.na.mysns.model.entity.UserEntity;
import com.na.mysns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Test
    void 회원가입이_정상동작(){
        String userName = "userName";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty()); // 회원가입된 이력이 없어야함
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));

        Assertions.assertDoesNotThrow(()-> userService.join(userName, password));
    }

    @Test
    void 회원가입시_이미_회원가입된_userName_존재(){
        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName, password);


        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture)); // 회원가입된 이력이 없어야함
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, ()->userService.join(userName, password));
    }

    @Test
    void 로그인_정상동작(){
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        Assertions.assertDoesNotThrow(()-> userService.login(userName, password));
    }

    @Test
    void 로그인시_userName으로_회원가입한_유저가_없는_경우(){
        String userName = "userName";
        String password = "password";


        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty()); // 회원가입된 이력이 없어야함

        Assertions.assertThrows(SnsApplicationException.class, ()-> userService.join(userName, password));
    }

    @Test
    void 로그인시_password_틀린_경우(){
        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture)); // 회원가입된 이력이 없어야함

        Assertions.assertThrows(SnsApplicationException.class, ()-> userService.join(userName, wrongPassword));
    }
}
