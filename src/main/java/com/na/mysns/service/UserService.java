package com.na.mysns.service;

import com.na.mysns.exception.ErrorCode;
import com.na.mysns.exception.SnsApplicationException;
import com.na.mysns.model.User;
import com.na.mysns.model.entity.UserEntity;
import com.na.mysns.repository.UserEntityRepository;
import com.na.mysns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public User loadUserByUserName(String userName){
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(()->
            new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));
    }

    // join 중 exception이 발생했을 경우 save가 롤백
    @Transactional
    public User join(String userName, String password) {
        // userName으로 회원가입된 user가 있는지
       userEntityRepository.findByUserName(userName).ifPresent((it)->{
            throw new SnsApplicationException(ErrorCode.DUPLICATE_USER_NAME,
                    String.format("%s is duplicated", userName));
        });

        // 회원가입 진행 = user를 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));
        // throw new RuntimeException();
        return User.fromEntity(userEntity);
    }

    // JWT - 어떤 유저인지를 암호화된 문자열을 통해 인증
    // 토큰반환 메소드
    public String login(String userName, String password) {
        // 회원가입 여부
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()
                -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));
        // 비밀번호 체크
        // if(!userEntity.getPassword().equals(password)){
        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);
        return token;
    }
}
