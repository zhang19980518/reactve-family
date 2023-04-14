package com.joert.dbcio.repository;

import com.joert.dbcio.entity.RolePermission;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@Repository
public interface RolePermissionRepository extends R2dbcRepository<RolePermission, Long> {


    Flux<RolePermission> findPermissionByRoleId(Long roleId);
}
