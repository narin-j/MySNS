package com.na.mysns.repository;

import com.na.mysns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    // 없을수도 있으니 Optional
    Optional<UserEntity> findByUserName(String userName);
}
