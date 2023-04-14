package com.joert.dbcio.repository;

import com.joert.dbcio.entity.UserRole;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@Repository
public interface UserRoleRepository extends R2dbcRepository<UserRole, Long> {

    /**
     * 根据用户名查找
     * @param userId
     * @return
     */
    Flux<UserRole> findRolesByUserId(Long userId);
}
