package com.na.mysns.service;

import com.na.mysns.exception.ErrorCode;
import com.na.mysns.exception.SnsApplicationException;
import com.na.mysns.model.Post;
import com.na.mysns.model.entity.PostEntity;
import com.na.mysns.model.entity.UserEntity;
import com.na.mysns.repository.PostEntityRepository;
import com.na.mysns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public Post modify(String title, String body, String userName, Integer postId){
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->
                new  SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        // post exist
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(()->
            new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        // post permission
        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String userName, Integer postId){
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->
                new  SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        // post exist
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(()->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postEntityRepository.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable){
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable){
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
        return postEntityRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }
}
