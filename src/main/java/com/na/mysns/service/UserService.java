package com.na.mysns.service;

import com.na.mysns.exception.ErrorCode;
import com.na.mysns.exception.SnsApplicationException;
import com.na.mysns.model.User;
import com.na.mysns.model.entity.UserEntity;
import com.na.mysns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String userName, String password) {
        // userName으로 회원가입된 user가 있는지
       userEntityRepository.findByUserName(userName).ifPresent((it)->{
            throw new SnsApplicationException(ErrorCode.DUPLICATE_USER_NAME,
                    String.format("%s is duplicated", userName));
        });

        // 회원가입 진행 = user를 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, password));

        return User.fromEntity(userEntity);
    }

    // JWT - 어떤 유저인지를 암호화된 문자열을 통해 인증
    // 토큰반환 메소드
    public String login(String userName, String password) {
        // 회원가입 여부
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()
                -> new SnsApplicationException(ErrorCode.DUPLICATE_USER_NAME, ""));
        // 비밀번호 체크
        if(!userEntity.getPassword().equals(password)){
            throw new SnsApplicationException(ErrorCode.DUPLICATE_USER_NAME, "");
        }
        // 토큰 생성
        return "";
    }
}
