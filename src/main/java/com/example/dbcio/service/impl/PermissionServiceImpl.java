package com.example.dbcio.service.impl;


import com.example.dbcio.common.BaseServiceImpl;
import com.example.dbcio.entity.Permission;
import com.example.dbcio.entity.User;
import com.example.dbcio.repository.PermissionRepository;
import com.example.dbcio.repository.UserRepository;
import com.example.dbcio.service.PermissionService;
import com.example.dbcio.service.UserService;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

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
