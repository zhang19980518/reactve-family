package com.example.dbcio.service;


import com.example.dbcio.common.BaseService;
import com.example.dbcio.entity.Permission;
import com.example.dbcio.entity.User;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
public interface PermissionService extends BaseService<Permission, Long> {

    /**
     * 根据路径和方法查找权限
     * @param path
     * @param httpMethod
     * @return
     */
    Mono<Permission> findPermissionsByPathAndMethod(String path, String httpMethod);
}
