package com.example.dbcio.repository;

import com.example.dbcio.entity.Permission;
import com.example.dbcio.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@Repository
public interface PermissionRepository extends R2dbcRepository<Permission, Long> {

    /**
     * 根据路由和方法查找
     * @param path
     * @param httpMethod
     * @return
     */
    Mono<Permission> findPermissionsByPathAndMethod(String path, String httpMethod);
}
