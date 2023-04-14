package com.example.dbcio.repository;

import com.example.dbcio.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {

    /**
     * 根据用户名查找
     * @param account
     * @return
     */
    Mono<User> findByUsername(String account);
}
