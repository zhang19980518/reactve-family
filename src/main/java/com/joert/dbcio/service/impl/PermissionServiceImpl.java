package com.joert.dbcio.service.impl;


import com.joert.dbcio.common.BaseServiceImpl;
import com.joert.dbcio.entity.Permission;
import com.joert.dbcio.repository.PermissionRepository;
import com.joert.dbcio.service.PermissionService;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long, PermissionRepository> implements PermissionService {
    public PermissionServiceImpl(R2dbcEntityTemplate r2dbcEntityTemplate, PermissionRepository permissionRepository) {
        super(permissionRepository, r2dbcEntityTemplate, Permission.class);
    }


    @Override
    public Mono<Permission> findPermissionsByPathAndMethod(String path, String httpMethod) {
        return getRepository().findPermissionsByPathAndMethod(path, httpMethod);
    }
}
