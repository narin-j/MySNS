package com.na.mysns.model.entity;

import com.na.mysns.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity(name="\"user\"")
@Table
@SQLDelete(sql = "UPDATED \"user\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL") // 삭제된 애들(삭제시간이 있는)을 제외한 데이터
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_name")
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name= "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER; // 권한정보 - String으로 관리해도 되지만 enum 클래스로 관리하는것이 좋은 방법
    // db에 저장할 때 생성일/업데이트시간/삭제시간 넣어주면 데이터 관리 용이
    @Column(name="registered_at")
    private Timestamp registeredAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    // row를 실제로 삭제하지 않고 데이터는 남겨둠
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt(){
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt(){
        this.updatedAt = Timestamp.from(Instant.now());
    }

    // 새 유저 엔티티를 만들어주는 메소드
    public static UserEntity of(String userName, String password){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }

}
