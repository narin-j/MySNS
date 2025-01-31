package com.na.mysns.service;

import com.na.mysns.exception.ErrorCode;
import com.na.mysns.exception.SnsApplicationException;
import com.na.mysns.model.entity.PostEntity;
import com.na.mysns.model.entity.UserEntity;
import com.na.mysns.repository.PostEntityRepository;
import com.na.mysns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName){
        // find user
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()
                ->new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
        // save post
        postEntityRepository.save(PostEntity.of(title, body, userEntity));
        // return
    }
}
