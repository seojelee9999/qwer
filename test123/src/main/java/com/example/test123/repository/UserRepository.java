package com.example.test123.repository;


import com.example.test123.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 사용자 찾기 (중복 체크용)
    boolean existsByEmail(String email);
}