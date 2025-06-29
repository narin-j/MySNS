package com.na.mysns.repository;

import com.na.mysns.model.Post;
import com.na.mysns.model.entity.LikeEntity;
import com.na.mysns.model.entity.PostEntity;
import com.na.mysns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);
}
